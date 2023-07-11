package com.chrrissoft.broadcastreceiver.work

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.chrrissoft.broadcastreceiver.CustomNotificationManager.Channels.WORKS_CHANNEL_ID
import com.chrrissoft.broadcastreceiver.work.ReceiverDataToWork.Companion.getReceiver
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject
import kotlin.random.Random.Default.nextInt

@HiltWorker
class BroadcastsChainWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted parameters: WorkerParameters,
    private val back: BroadcastsChainBack
) : Worker(context, parameters) {
    @Inject
    lateinit var workManager: WorkManager

    var workCancelled = false

    override fun doWork(): Result {
        if (workCancelled) return Result.failure()
        return when (back.getResult(inputData.getReceiver)) {
            is BroadcastsChainBackState.Failure -> Result.failure()
            BroadcastsChainBackState.None -> throw IllegalStateException()
            BroadcastsChainBackState.Success -> Result.success()
        }
    }

    override fun getForegroundInfo(): ForegroundInfo {
        val notification = NotificationCompat.Builder(context, WORKS_CHANNEL_ID)
            .setContentTitle("Working - tap to stop broadcast")
            .setSmallIcon(android.R.drawable.alert_dark_frame)
            .setContentIntent(workManager.createCancelPendingIntent(this.id))
            .build()
        return ForegroundInfo(nextInt(), notification)
    }

    override fun onStopped() {
        workCancelled = true
        back.onCancelWork(inputData.getReceiver)
        super.onStopped()
    }

    companion object {
        const val TAG = "PENDING_RESULT"

        const val workMangerNotificationId = 0
        const val backNotificationId = 1
    }
}
