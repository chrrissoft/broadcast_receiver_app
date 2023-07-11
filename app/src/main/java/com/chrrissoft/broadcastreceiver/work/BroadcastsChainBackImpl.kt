package com.chrrissoft.broadcastreceiver.work

import android.util.Log
import com.chrrissoft.broadcastreceiver.CustomNotificationManager
import com.chrrissoft.broadcastreceiver.CustomNotificationManager.Channels.WORKS_CHANNEL_ID
import com.chrrissoft.broadcastreceiver.customs.CustomReceiver.Companion.setCommonExtra
import com.chrrissoft.broadcastreceiver.customs.ReceiverResState
import com.chrrissoft.broadcastreceiver.customs.ReceiverResState.Failure
import com.chrrissoft.broadcastreceiver.customs.ReceiverResState.Success
import com.chrrissoft.broadcastreceiver.work.BroadcastsChainBackState.BroadcastsChainBackStateQualifier
import com.chrrissoft.broadcastreceiver.work.ReceiverDataToWork.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.random.Random.Default.nextInt

class BroadcastsChainBackImpl @Inject constructor(
    @ReceiverResState.ContextReceiverResState0
    private val contextResState0: MutableStateFlow<ReceiverResState>,
    @ReceiverResState.ContextReceiverResState1
    private val contextResState1: MutableStateFlow<ReceiverResState>,
    @ReceiverResState.ContextReceiverResState2
    private val contextResState2: MutableStateFlow<ReceiverResState>,


    @ReceiverResState.ManifestReceiverResState0
    private val manifestResState0: MutableStateFlow<ReceiverResState>,
    @ReceiverResState.ManifestReceiverResState1
    private val manifestResState1: MutableStateFlow<ReceiverResState>,
    @ReceiverResState.ManifestReceiverResState2
    private val manifestResState2: MutableStateFlow<ReceiverResState>,


    @BroadcastsChainBackStateQualifier
    override val state: MutableStateFlow<BroadcastsChainBackState>,

    private val notificationManager: CustomNotificationManager,
) : BroadcastsChainBack {
    var cancelWork = false

    override fun getResult(receiver: ReceiverDataToWork): BroadcastsChainBackState {
        cancelWork = false
        notificationManager.sendNotification(
            id = PROGRESS_NOTIFICATION_ID,
            title = "back work",
            channelId = WORKS_CHANNEL_ID,
        )
        repeat(100) {
            notificationManager.sendBackWorkProgressNotification(it, PROGRESS_NOTIFICATION_ID)
            Thread.sleep(120)
            if (cancelWork) {
                notificationManager.deleteNotification(id = PROGRESS_NOTIFICATION_ID)
                update(getState(receiver), true)
                return BroadcastsChainBackState.Failure
            }
        }
        if (!cancelWork) {
            update(getState(receiver))
            Log.d(TAG, "on finish success: $receiver")
        }
        notificationManager.deleteNotification(id = PROGRESS_NOTIFICATION_ID)
        Log.d(TAG, "on finish: $receiver")
        return BroadcastsChainBackState.Success
    }

    override fun onCancelWork(receiver: ReceiverDataToWork) {
        cancelWork = true
        Log.d(TAG, "on cancel: $receiver")
    }

    private fun getState(receiver: ReceiverDataToWork) = when (receiver) {
        CONTEXT_0 -> contextResState0
        CONTEXT_1 -> contextResState1
        CONTEXT_2 -> contextResState2
        MANIFEST_0 -> manifestResState0
        MANIFEST_1 -> manifestResState1
        MANIFEST_2 -> manifestResState2
    }

    private fun update(state: MutableStateFlow<ReceiverResState>, failed: Boolean = false) {
        state.update {
            if (failed) Failure()
            else Success(
                code = nextInt(9),
                data = "data",
                extra = setCommonExtra
            )
        }
    }


    private companion object {
        private const val TAG = "BroadcastsChainBackImpl"
        private const val PROGRESS_NOTIFICATION_ID = 100
    }
}
