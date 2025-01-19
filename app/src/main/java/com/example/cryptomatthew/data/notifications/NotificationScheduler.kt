package com.example.cryptomatthew.data.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject


enum class NotificationChannelId(
    val id: String,
    val name_: String,
    val descriptionText: String,
    val priority: Int
) {
    EXCHANGE_RATES(
        "exchange_rates",
        "Курс криптовалют",
        "Курс криптовалют, выбранных на экране 'Уведомления'",
        NotificationManager.IMPORTANCE_DEFAULT
    )
}

class NotificationScheduler @Inject constructor(
    @ApplicationContext val context: Context
) {

    private fun createNotificationIntent(
        notificationId: Int,
        channel: NotificationChannelId
    ): PendingIntent {
        val notificationIntent = Intent(context, NotificationReceiver::class.java)
        notificationIntent.putExtra("notificationId", notificationId)
        notificationIntent.putExtra("channelId", channel.id)

        return PendingIntent.getBroadcast(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createNotificationChannel(channel: NotificationChannelId) {
        // Create the NotificationChannel.
        val mChannel = NotificationChannel(channel.id, channel.name_, channel.priority)
        mChannel.description = channel.descriptionText
        // Register the channel with the system. You can't change the importance
        // or other notification behaviors after this.
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }

    fun cancelNotification(notificationId: Int) {
        // extras are not taken into account when comparing
        val pendingIntent =
            createNotificationIntent(notificationId, NotificationChannelId.EXCHANGE_RATES)
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)

    }

    fun scheduleRepeatingNotification(
        calendar: Calendar,
        intervalMs: Long,
        notificationId: Int,
        channel: NotificationChannelId
    ) {
        createNotificationChannel(channel)
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val pendingIntent = createNotificationIntent(notificationId, channel)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            intervalMs,
            pendingIntent
        )

    }


}
