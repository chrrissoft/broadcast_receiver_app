package com.chrrissoft.broadcastreceiver.common.ui

import com.chrrissoft.broadcastreceiver.common.CommonScreenViewModel.CommonsBroadcastsScreenEventsHandler
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState.ContextState
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState.ManifestState

sealed interface CommonsBroadcastsScreenEvent {
    fun handle(handler: CommonsBroadcastsScreenEventsHandler) {
        when (this) {
            is OnChangeContextPageState -> handler.onEvent(this)
            is OnChangeManifestPageState -> handler.onEvent(this)
        }
    }

    data class OnChangeContextPageState(val data: ContextState)
        : CommonsBroadcastsScreenEvent

    data class OnChangeManifestPageState(val data: ManifestState)
        : CommonsBroadcastsScreenEvent
}
