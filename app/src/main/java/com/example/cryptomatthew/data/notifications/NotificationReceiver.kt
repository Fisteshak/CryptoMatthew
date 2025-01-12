package com.example.cryptomatthew.data.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.cryptomatthew.R
import com.example.cryptomatthew.data.CurrenciesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {
    @Inject lateinit var currenciesRepository: CurrenciesRepository
    // creates notification when it is time
    override fun onReceive(context: Context, intent: Intent) = goAsync() { pendingResult ->
        val channelId = intent.getStringExtra("channelId")
        if (channelId == null) return@goAsync

        Log.d("NotificationReceiver", "called onReceive: ")

        val flow = currenciesRepository.getCurrencies()
        val old = flow.first().filter { it.rateNotificationsEnabled }

        currenciesRepository.updateCurrencies()

        val new = flow.first().filter { it.rateNotificationsEnabled }

        val notificationId = intent.getIntExtra("notificationId", 0)
        var str = ""
        old.zip(new).forEach {
            str += it.first.finsUSD.toString() + " -> " + it.second.finsUSD.toString() + "\n"
        }

        val message = intent.getStringExtra(str)
        val title = intent.getStringExtra("title")

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.chart_line)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true) // styling the notification

        notificationManager.notify(notificationId, notificationBuilder.build())


    }
}

fun BroadcastReceiver.goAsync(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.(BroadcastReceiver.PendingResult) -> Unit
) {
    val pendingResult = goAsync()
    @OptIn(DelicateCoroutinesApi::class) // Must run globally; there's no teardown callback.
    GlobalScope.launch(context) {
        try {
            block(pendingResult)
        } finally {
            pendingResult.finish()
        }
    }
}
