package com.chrrissoft.broadcastreceiver.customs.ui

import com.chrrissoft.broadcastreceiver.CtxRegistration
import com.chrrissoft.broadcastreceiver.CtxRegistration.Activity
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.ContextPageState.ContextRegistrations.ContextRegistration
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.ManifestPageState.ManifestEnableds.ManifestEnabled
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.Page.Manifest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.onEach


data class CustomsBroadcastsScreenState(
    val page: Page = Manifest,
    val contextPageState: ContextPageState = ContextPageState(),
    val manifestPageState: ManifestPageState = ManifestPageState(),
) {

    val registration0 get() = contextPageState.registrations.registration0
    val registration1 get() = contextPageState.registrations.registration1
    val registration2 get() = contextPageState.registrations.registration2

    val isRegisteredInActivityRegistration0 get() = registration0.registered && registration0.context==Activity
    val isRegisteredInActivityRegistration1 get() = registration1.registered && registration1.context==Activity
    val isRegisteredInActivityRegistration2 get() = registration2.registered && registration2.context==Activity


    val enabled0 get() = manifestPageState.enableds.enabled0
    val enabled1 get() = manifestPageState.enableds.enabled1
    val enabled2 get() = manifestPageState.enableds.enabled2

    val enableds get() = manifestPageState.enableds
    val manifestSender get() = manifestPageState.sender

    val registrations get() = contextPageState.registrations
    val contextSender get() = contextPageState.sender

    enum class Page {
        Context, Manifest
    }

    data class ManifestPageState(
        val sender: Sender = Sender(),
        val senderCompanion: Sender = Sender(),
        val enableds: ManifestEnableds = ManifestEnableds(),
    ) {
        data class ManifestEnableds(
            val enabled0: ManifestEnabled = ManifestEnabled(),
            val enabled1: ManifestEnabled = ManifestEnabled(),
            val enabled2: ManifestEnabled = ManifestEnabled(),
        ) {
            data class ManifestEnabled(
                val finish: Boolean = false,
                val enabled: Boolean = false,
            ) {
                constructor(state: Boolean) : this(!state, state)
            }
        }
    }

    data class ContextPageState(
        val sender: Sender = Sender(),
        val senderCompanion: Sender = Sender(),
        val registrations: ContextRegistrations = ContextRegistrations(),
    ) {
        data class ContextRegistrations(
            val registration0: ContextRegistration = ContextRegistration(),
            val registration1: ContextRegistration = ContextRegistration(),
            val registration2: ContextRegistration = ContextRegistration(),
        ) {
            data class ContextRegistration(
                val enabled: Boolean = true,
                val registered: Boolean = false,
                val context: CtxRegistration = Activity,
                val permission: Permission = Permission.WithOut,
            )
        }
    }

    data class Sender(
        val order: Boolean = false,
        val enabled: Boolean = true,
        val permission: Permission = Permission.WithOut,
    )

    companion object {
        fun StateFlow<CustomsBroadcastsScreenState>.observeManifest0(
            onEnabled: (ManifestEnabled) -> Unit,
            onDisabled: (ManifestEnabled) -> Unit,
        ): Flow<CustomsBroadcastsScreenState> {
            return distinctUntilChangedBy { it.enabled0 }
                .onEach {
                    if (it.enabled0.enabled) onEnabled(it.enabled0)
                    else onDisabled(it.enabled0)
                }
        }

        fun StateFlow<CustomsBroadcastsScreenState>.observeManifest1(
            onEnabled: (ManifestEnabled) -> Unit,
            onDisabled: (ManifestEnabled) -> Unit,
        ): Flow<CustomsBroadcastsScreenState> {
            return distinctUntilChangedBy { it.enabled1 }
                .onEach {
                    if (it.enabled1.enabled) onEnabled(it.enabled1)
                    else onDisabled(it.enabled1)
                }
        }

        fun StateFlow<CustomsBroadcastsScreenState>.observeManifest2(
            onEnabled: (ManifestEnabled) -> Unit,
            onDisabled: (ManifestEnabled) -> Unit,
        ): Flow<CustomsBroadcastsScreenState> {
            return distinctUntilChangedBy { it.enabled2 }
                .onEach {
                    if (it.enabled2.enabled) onEnabled(it.enabled2)
                    else onDisabled(it.enabled2)
                }
        }


        fun StateFlow<CustomsBroadcastsScreenState>.observeCtx0(
            onRegistrationChanged: (ContextRegistration) -> Unit,
            onUnregistration: (ContextRegistration) -> Unit,
        ): Flow<CustomsBroadcastsScreenState> {
            return distinctUntilChangedBy { it.registration0 }
                .onEach {
                    if (it.registration0.registered) onRegistrationChanged(it.registration0)
                    else onUnregistration(it.registration0)
                }
        }

        fun StateFlow<CustomsBroadcastsScreenState>.observeCtx1(
            onRegistrationChanged: (ContextRegistration) -> Unit,
            onUnregistration: (ContextRegistration) -> Unit,
        ): Flow<CustomsBroadcastsScreenState> {
            return distinctUntilChangedBy { it.registration1 }
                .onEach {
                    if (it.registration1.registered) onRegistrationChanged(it.registration1)
                    else onUnregistration(it.registration1)
                }
        }

        fun StateFlow<CustomsBroadcastsScreenState>.observeCtx2(
            onRegistrationChanged: (ContextRegistration) -> Unit,
            onUnregistration: (ContextRegistration) -> Unit,
        ): Flow<CustomsBroadcastsScreenState> {
            return distinctUntilChangedBy { it.registration2 }
                .onEach {
                    if (it.registration2.registered) onRegistrationChanged(it.registration2)
                    else onUnregistration(it.registration2)
                }
        }
    }
}
