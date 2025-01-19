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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.math.min

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {
    @Inject
    lateinit var currenciesRepository: CurrenciesRepository

    // creates notification when it is time
    override fun onReceive(context: Context, intent: Intent) = goAsync { pendingIntent ->
        Log.d("NotificationReceiver", "called on receive")
        val channelId = intent.getStringExtra("channelId")
        if (channelId == null) throw IllegalArgumentException("NotificationReceiver didn't receive channelId in intent")
        val notificationId = intent.getIntExtra("notificationId", 0)

        val title = "Курсы криптовалют"

        val old = currenciesRepository.getCurrencies().filter { it.rateNotificationsEnabled }
        currenciesRepository.updateCurrencies(terminateAfter = 1)
        val new = currenciesRepository.getCurrencies().filter { it.rateNotificationsEnabled }


        val len = min(old.size, new.size)
        var message = ""

        old.zip(new).forEachIndexed { index, it ->
            message +=
                it.first.symbol + ":  " +
                        (it.first.finsUSD?.price?.formatLong() ?: "") +
                        " -> " +
                        (it.second.finsUSD?.price?.formatLong()
                            ?: "") + (if (index != len - 1) "\n" else "")
        }

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.chart_line)
            .setContentTitle(title)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
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
    with(Dispatchers.IO) {
        GlobalScope.launch(context) {
            block(pendingResult)
        }.invokeOnCompletion {
            pendingResult.finish()
        }
    }
}
