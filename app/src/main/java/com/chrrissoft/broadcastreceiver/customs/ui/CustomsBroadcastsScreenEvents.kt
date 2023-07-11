package com.chrrissoft.broadcastreceiver.customs.ui

import com.chrrissoft.broadcastreceiver.customs.CustomScreenViewModel.ContextPageEventHandler
import com.chrrissoft.broadcastreceiver.customs.CustomScreenViewModel.ManifestPageEventHandler
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.ContextPageState.ContextRegistrations
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.ManifestPageState.ManifestEnableds
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.Page
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.Page.Manifest
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.Sender

object CustomsBroadcastsScreenEvents {
    sealed interface ManifestPageEvent {
        fun handle(handler: ManifestPageEventHandler) {
            when (this) {
                is OnEnabledsChange -> handler.onEvent(this)
                is OnSenderChange -> handler.onEvent(this)
                is OnSwitchPage -> handler.onEvent(this)
                is OnSenderCompanionChange -> handler.onEvent(this)
            }
        }

        data class OnSenderChange(val sender: Sender) : ManifestPageEvent
        data class OnSenderCompanionChange(val sender: Sender) : ManifestPageEvent
        data class OnEnabledsChange(val res: ManifestEnableds) : ManifestPageEvent
        data class OnSwitchPage(val page: Page = Page.Context) : ManifestPageEvent
    }

    sealed interface ContextPageEvent {
        fun handle(handler: ContextPageEventHandler) {
            when (this) {
                is OnRegistrationsChange -> handler.onEvent(this)
                is OnSenderChange -> handler.onEvent(this)
                is OnSwitchPage -> handler.onEvent(this)
                is OnSenderCompanionChange -> handler.onEvent(this)
            }
        }

        data class OnSenderChange(val sender: Sender) : ContextPageEvent
        data class OnSenderCompanionChange(val sender: Sender) : ContextPageEvent
        data class OnRegistrationsChange(val res: ContextRegistrations) : ContextPageEvent
        data class OnSwitchPage(val page: Page = Manifest) : ContextPageEvent
    }
}
