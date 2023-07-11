package com.chrrissoft.broadcastreceiver.customs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.chrrissoft.broadcastreceiver.CustomNotificationManager
import com.chrrissoft.broadcastreceiver.CustomNotificationManager.Channels.RECEIVER_CHANNEL_ID
import com.chrrissoft.broadcastreceiver.customs.CustomReceiver.Companion.CUSTOM_CONTEXT_BROADCASTS
import com.chrrissoft.broadcastreceiver.customs.CustomReceiver.Companion.CUSTOM_MANIFEST_BROADCASTS
import com.chrrissoft.broadcastreceiver.customs.CustomReceiver.Companion.getCommonExtra
import com.chrrissoft.broadcastreceiver.work.BroadcastsChainWorker
import com.chrrissoft.broadcastreceiver.work.ReceiverDataToWork
import com.chrrissoft.broadcastreceiver.work.ReceiverDataToWork.Companion.setReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Qualifier

class CommonBehaviorReceiver @Inject constructor(
    private val state: MutableStateFlow<ReceiverResState>,
    private val notificationManager: CustomNotificationManager,
    private val scope: CoroutineScope,
    private val workManager: WorkManager
) {


    fun onReceive(receiver: CustomReceiver, context: Context?, intent: Intent?) {
        Log.d(TAG, "on custom broadcast...")

        if (context==null) return
        if (intent==null) return
        if (intent.action!=CUSTOM_CONTEXT_BROADCASTS &&
            intent.action!=CUSTOM_MANIFEST_BROADCASTS) return

        notification(receiver)
        if (receiver.isOrderedBroadcast) {
            startWork(receiver.receiver)
            val pendingResult = receiver.goAsync()
            observe(pendingResult)
        }
    }

    private fun notification(receiver: CustomReceiver) {
        if (receiver.isOrderedBroadcast) {
            notificationManager.sendNotification(
                title = receiver.title,
                subText = "sorted broadcast",
                text = "code: ${receiver.resultCode}\ndata: ${receiver.resultData}\nextra: ${receiver.getCommonExtra}",
                channelId = RECEIVER_CHANNEL_ID,
                id = NOTIFICATION_ID
            )
        } else {
            notificationManager.sendNotification(
                title = receiver.title,
                subText = "unsorted broadcast",
                channelId = RECEIVER_CHANNEL_ID
            )
        }
    }

    private fun startWork(receiver: ReceiverDataToWork) {
        val request = OneTimeWorkRequest.Builder(BroadcastsChainWorker::class.java)
            .setExpedited(OutOfQuotaPolicy.DROP_WORK_REQUEST)
            .setInputData(Data.Builder().setReceiver(receiver).build())
            .build()
        workManager.enqueue(request)
    }

    private fun observe(pendingResult: BroadcastReceiver.PendingResult) {
        scope.launch {
            state.collect {
                when (it) {
                    ReceiverResState.None -> {}
                    is ReceiverResState.Failure -> {
                        pendingResult.abortBroadcast()
                        pendingResult.finish()
                        notificationManager.deleteNotification(NOTIFICATION_ID)
                    }
                    is ReceiverResState.Success -> {
                        Log.d(TAG, "OnSuccessRes: $pendingResult")
                        pendingResult.setResult(it.code, it.data, it.extra)
                        pendingResult.finish()
                        notificationManager.deleteNotification(NOTIFICATION_ID)
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "CommonBehaviorReceiver"
        private const val NOTIFICATION_ID = 17

        @Qualifier
        @Retention(AnnotationRetention.BINARY)
        annotation class QualifierContextReceiver0

        @Qualifier
        @Retention(AnnotationRetention.BINARY)
        annotation class QualifierContextReceiver1

        @Qualifier
        @Retention(AnnotationRetention.BINARY)
        annotation class QualifierContextReceiver2


        @Qualifier
        @Retention(AnnotationRetention.BINARY)
        annotation class QualifierManifestReceiver0

        @Qualifier
        @Retention(AnnotationRetention.BINARY)
        annotation class QualifierManifestReceiver1

        @Qualifier
        @Retention(AnnotationRetention.BINARY)
        annotation class QualifierManifestReceiver2
    }

}
