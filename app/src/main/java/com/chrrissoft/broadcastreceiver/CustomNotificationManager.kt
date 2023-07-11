package com.chrrissoft.broadcastreceiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.chrrissoft.broadcastreceiver.CustomNotificationManager.Channels.RECEIVER_CHANNEL_ID
import com.chrrissoft.broadcastreceiver.CustomNotificationManager.Channels.RECEIVER_CHANNEL_NAME
import com.chrrissoft.broadcastreceiver.CustomNotificationManager.Channels.WORKS_CHANNEL_ID
import com.chrrissoft.broadcastreceiver.CustomNotificationManager.Channels.WORKS_CHANNEL_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val manager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(WORKS_CHANNEL_ID, WORKS_CHANNEL_NAME, IMPORTANCE_LOW)
            createChannel(RECEIVER_CHANNEL_ID, RECEIVER_CHANNEL_NAME)
        }
    }

    fun sendNotification(
        title: String,
        channelId: String,
        subText: String = "",
        text: String = "",
        info: String = "",
        icon: Int = android.R.drawable.stat_notify_chat,
        id: Int = UUID.randomUUID().hashCode(),
    ) {
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setSubText(subText)
            .setContentText(text)
            .setContentInfo(info)
            .setContentTitle(title)

        with(NotificationManagerCompat.from(context)) {
            notify(id, notification.build())
        }
    }

    fun sendBackWorkProgressNotification(progress: Int, id: Int) {
        val notification = NotificationCompat.Builder(context, WORKS_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_notify_chat)
            .setContentTitle("Back progress")
            .setProgress(100, progress, false)

        with(NotificationManagerCompat.from(context)) {
            notify(id, notification.build())
        }
    }

    fun deleteNotification(id: Int) {
        with(NotificationManagerCompat.from(context)) {
            this.cancel(id)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(
        id: String,
        name: String,
        importance: Int = NotificationManager.IMPORTANCE_HIGH,
    ) {
        NotificationChannel(id, name, importance).apply {
            manager.createNotificationChannel(this)
        }
    }

    object Channels {
        const val RECEIVER_CHANNEL_ID = "com.chrrissoft.broadcastreceiver.ReceiverChannelId"
        const val WORKS_CHANNEL_ID = "com.chrrissoft.broadcastreceiver.WorksChannelId"

        const val RECEIVER_CHANNEL_NAME = "Receiver Channel"
        const val WORKS_CHANNEL_NAME = "Works Channel"
    }

    private companion object {
        private const val TAG = "CustomNotificationManager"
    }
}
