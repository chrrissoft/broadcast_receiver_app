package com.chrrissoft.broadcastreceiver.customs

import androidx.lifecycle.ViewModel
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenEvents.ContextPageEvent
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenEvents.ManifestPageEvent
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CustomScreenViewModel @Inject constructor(
    private val _state: MutableStateFlow<CustomsBroadcastsScreenState>,
) : ViewModel() {
    val stateFlow = _state.asStateFlow()
    val state get() = stateFlow.value

    private val contextPageEventHandler = ContextPageEventHandler()
    private val manifestPageEventHandler = ManifestPageEventHandler()


    fun handleContextPageEvent(event: ContextPageEvent) {
        event.handle(contextPageEventHandler)
    }

    fun handleManifestPageEvent(event: ManifestPageEvent) {
        event.handle(manifestPageEventHandler)
    }

    fun handleActivityDestroy() {
        if (state.isRegisteredInActivityRegistration0)
            _state.update {
                val registration0 = it.registration0.copy(registered = false)
                val registrations = it.registrations.copy(registration0 = registration0)
                val page = it.contextPageState.copy(registrations = registrations)
                it.copy(contextPageState = page)
            }

        if (state.isRegisteredInActivityRegistration1)
            _state.update {
                val registration1 = it.registration1.copy(registered = false)
                val registrations = it.registrations.copy(registration1 = registration1)
                val page = it.contextPageState.copy(registrations = registrations)
                it.copy(contextPageState = page)
            }

        if (state.isRegisteredInActivityRegistration2)
            _state.update {
                val registration2 = it.registration2.copy(registered = false)
                val registrations = it.registrations.copy(registration2 = registration2)
                val page = it.contextPageState.copy(registrations = registrations)
                it.copy(contextPageState = page)
            }
    }

    inner class ContextPageEventHandler {
        fun onEvent(event: ContextPageEvent.OnSenderChange) {
            _state.update {
                val newState = it.contextPageState.copy(sender = event.sender)
                it.copy(contextPageState = newState)
            }
        }

        fun onEvent(event: ContextPageEvent.OnRegistrationsChange) {
            _state.update {
                val newState = it.contextPageState.copy(registrations = event.res)
                it.copy(contextPageState = newState)
            }
        }

        fun onEvent(event: ContextPageEvent.OnSwitchPage) {
            _state.update { it.copy(page = event.page) }
        }

        fun onEvent(event: ContextPageEvent.OnSenderCompanionChange) {
            _state.update {
                val newState = it.contextPageState.copy(senderCompanion = event.sender)
                it.copy(contextPageState = newState)
            }
        }
    }

    inner class ManifestPageEventHandler {
        fun onEvent(event: ManifestPageEvent.OnSenderChange) {
            _state.update {
                val newState = it.manifestPageState.copy(sender = event.sender)
                it.copy(manifestPageState = newState)
            }
        }

        fun onEvent(event: ManifestPageEvent.OnEnabledsChange) {
            _state.update {
                val newState = it.manifestPageState.copy(enableds = event.res)
                it.copy(manifestPageState = newState)
            }
        }

        fun onEvent(event: ManifestPageEvent.OnSwitchPage) {
            _state.update { it.copy(page = event.page) }
        }

        fun onEvent(event: ManifestPageEvent.OnSenderCompanionChange) {
            _state.update {
                val newState = it.manifestPageState.copy(senderCompanion = event.sender)
                it.copy(manifestPageState = newState)
            }
        }
    }
}
