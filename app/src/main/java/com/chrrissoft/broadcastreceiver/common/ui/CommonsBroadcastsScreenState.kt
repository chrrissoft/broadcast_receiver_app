package com.chrrissoft.broadcastreceiver.common.ui

import com.chrrissoft.broadcastreceiver.CtxRegistration
import com.chrrissoft.broadcastreceiver.CtxRegistration.Activity
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState.ContextState.Registration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach


data class CommonsBroadcastsScreenState(
    val contextState: ContextState = ContextState(),
    val manifestState: ManifestState = ManifestState(),
) {
    val inputMethodIsRegisteredInActivity
        = contextState.inputMethod.registered && contextState.inputMethod.context == Activity

    val airPlaneIsRegisteredInActivity
            = contextState.airPlane.registered && contextState.airPlane.context == Activity

    val powerConnectionIsRegisteredInActivity
            = contextState.powerConnection.registered && contextState.powerConnection.context == Activity


    data class ManifestState(
        val timeZone: Boolean = false,
        val bootComplete: Boolean = false,
        val bluetoothHeadset: Boolean = false,
    )

    data class ContextState(
        val airPlane: Registration = Registration(),
        val inputMethod: Registration = Registration(),
        val powerConnection: Registration = Registration(),
    ) {
        data class Registration(
            val registered: Boolean = false,
            val context: CtxRegistration = Activity
        )
    }


    companion object {
        fun StateFlow<CommonsBroadcastsScreenState>.observeBoot(
            block: (Boolean) -> Unit,
        ): Flow<CommonsBroadcastsScreenState> {
            return onEach { block(it.manifestState.bootComplete) }
        }

        fun StateFlow<CommonsBroadcastsScreenState>.observeTimeZone(
            block: (Boolean) -> Unit,
        ): Flow<CommonsBroadcastsScreenState> {
            return onEach { block(it.manifestState.timeZone) }
        }

        fun StateFlow<CommonsBroadcastsScreenState>.observeBluetoothHeadset(
            block: (Boolean) -> Unit,
        ): Flow<CommonsBroadcastsScreenState> {
            return onEach { block(it.manifestState.bluetoothHeadset) }
        }




        fun StateFlow<CommonsBroadcastsScreenState>.observeAirPlane(
            onRegistrationChanged: (Registration) -> Unit,
            onUnregistration: (Registration) -> Unit,
        ): Flow<CommonsBroadcastsScreenState> {
            return onEach {
                if (it.contextState.airPlane.registered) {
                    onRegistrationChanged(it.contextState.airPlane)
                } else onUnregistration(it.contextState.airPlane)
            }
        }

        fun StateFlow<CommonsBroadcastsScreenState>.observeInputMethod(
            onRegistrationChanged: (Registration) -> Unit,
            onUnregistration: (Registration) -> Unit,
        ): Flow<CommonsBroadcastsScreenState> {
            return onEach {
                if (it.contextState.inputMethod.registered) {
                    onRegistrationChanged(it.contextState.inputMethod)
                } else onUnregistration(it.contextState.inputMethod)
            }
        }

        fun StateFlow<CommonsBroadcastsScreenState>.observePowerConnection(
            onRegistrationChanged: (Registration) -> Unit,
            onUnregistration: (Registration) -> Unit,
        ): Flow<CommonsBroadcastsScreenState> {
            return onEach {
                if (it.contextState.powerConnection.registered) {
                    onRegistrationChanged(it.contextState.powerConnection)
                } else onUnregistration(it.contextState.powerConnection)
            }
        }

    }
}
