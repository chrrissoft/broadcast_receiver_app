package com.chrrissoft.broadcastreceiver.common

import androidx.lifecycle.ViewModel
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenEvent
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenEvent.OnChangeContextPageState
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenEvent.OnChangeManifestPageState
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState.ContextState.Registration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CommonScreenViewModel @Inject constructor(
    private val _state: MutableStateFlow<CommonsBroadcastsScreenState>
) : ViewModel() {
    val stateFlow = _state.asStateFlow()
    val state get() = stateFlow.value

    private val eventHandler = CommonsBroadcastsScreenEventsHandler()

    fun handleEvent(event: CommonsBroadcastsScreenEvent) {
        event.handle(eventHandler)
    }

    fun handleActivityDestroy() {
        if (state.airPlaneIsRegisteredInActivity) {
            _state.update {
                val context = it.contextState.copy(airPlane = Registration())
                it.copy(contextState = context)
            }
        }

        if (state.inputMethodIsRegisteredInActivity) {
            _state.update {
                val context = it.contextState.copy(inputMethod = Registration())
                it.copy(contextState = context)
            }
        }

        if (state.powerConnectionIsRegisteredInActivity) {
            _state.update {
                val context = it.contextState.copy(powerConnection = Registration())
                it.copy(contextState = context)
            }
        }
    }

    inner class CommonsBroadcastsScreenEventsHandler {
        fun onEvent(event: OnChangeContextPageState) {
            _state.update {
                it.copy(contextState = event.data)
            }
        }

        fun onEvent(event: OnChangeManifestPageState) {
            _state.update {
                it.copy(manifestState = event.data)
            }
        }
    }
}
