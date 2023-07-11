package com.chrrissoft.broadcastreceiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.pm.PackageManager.*
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.chrrissoft.broadcastreceiver.common.CommonReceiver.*
import com.chrrissoft.broadcastreceiver.common.CommonReceiver.AirPlaneModeChangedReceiver.Companion.registerAirPlane
import com.chrrissoft.broadcastreceiver.common.CommonReceiver.InputMethodChangedReceiver.Companion.registerInputMethod
import com.chrrissoft.broadcastreceiver.common.CommonReceiver.PowerConnectionReceiver.Companion.registerPowerConnection
import com.chrrissoft.broadcastreceiver.common.CommonScreenViewModel
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenEvent.OnChangeManifestPageState
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState.Companion.observeAirPlane
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState.Companion.observeBluetoothHeadset
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState.Companion.observeBoot
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState.Companion.observeInputMethod
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState.Companion.observePowerConnection
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState.Companion.observeTimeZone
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState.ManifestState
import com.chrrissoft.broadcastreceiver.customs.CustomReceiver.*
import com.chrrissoft.broadcastreceiver.customs.CustomReceiver.Companion.registerReceiver
import com.chrrissoft.broadcastreceiver.customs.CustomScreenViewModel
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenEvents.ManifestPageEvent.OnEnabledsChange
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.Companion.observeCtx0
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.Companion.observeCtx1
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.Companion.observeCtx2
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.Companion.observeManifest0
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.Companion.observeManifest1
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.Companion.observeManifest2
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.ManifestPageState.ManifestEnableds
import com.chrrissoft.broadcastreceiver.navigation.Graph
import com.chrrissoft.broadcastreceiver.ui.components.AppUiContainer
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {

    @Inject
    lateinit var app: BroadcastReceiverApp

    @Inject
    lateinit var airPlaneReceiver: AirPlaneModeChangedReceiver

    @Inject
    lateinit var inputMethodReceiver: InputMethodChangedReceiver

    @Inject
    lateinit var powerConnectionReceiver: PowerConnectionReceiver


    @Inject
    lateinit var custom0: ContextReceiver0

    @Inject
    lateinit var custom1: ContextReceiver1

    @Inject
    lateinit var custom2: ContextReceiver2


    private val customViewModel by viewModels<CustomScreenViewModel>()
    private val commonViewModel by viewModels<CommonScreenViewModel>()

    private val activityCtx by lazy { this }
    private val appCtx by lazy { this.applicationContext }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app.init()

        handleCommonManifestEnabled()
        handleCommonContextRegistrations()

        handleCustomManifestEnabled()
        handleCustomContextRegistrations()

        setContent {
            val customState by customViewModel.stateFlow.collectAsState()
            val commonState by commonViewModel.stateFlow.collectAsState()
            AppUiContainer {
                Graph(
                    customScreenState = customState,
                    onCustomContextPageEvent = { customViewModel.handleContextPageEvent(it) },
                    onCustomManifestPageEvent = { customViewModel.handleManifestPageEvent(it) },

                    commonScreenState = commonState,
                    onCommonScreenEvent = { commonViewModel.handleEvent(it) },
                )
            }
        }
    }

    private fun handleCommonManifestEnabled() {
        fun updateManifestState(state: ManifestState) {
            commonViewModel.handleEvent(OnChangeManifestPageState(state))
        }

        fun updateBoot(state: Boolean) {
            commonViewModel.state.manifestState.copy(bootComplete = state)
                .apply { updateManifestState(this) }
        }

        fun updateTimeZone(state: Boolean) {
            commonViewModel.state.manifestState.copy(timeZone = state)
                .apply { updateManifestState(this) }
        }

        fun updateBluetoothHeadset(state: Boolean) {
            commonViewModel.state.manifestState.copy(bluetoothHeadset = state)
                .apply { updateManifestState(this) }
        }

        getEnableReceiver(
            receiver = BootCompleteReceiver::class.java,
            default = { updateBoot(false) },
            disable = { updateBoot(false) },
            enable = { updateBoot(true) },
        )

        getEnableReceiver(
            receiver = TimeZoneChangedReceiver::class.java,
            default = { updateTimeZone(false) },
            disable = { updateTimeZone(false) },
            enable = { updateTimeZone(true) },
        )

        getEnableReceiver(
            receiver = BluetoothHeadsetChangeReceiver::class.java,
            default = { updateBluetoothHeadset(false) },
            disable = { updateBluetoothHeadset(false) },
            enable = { updateBluetoothHeadset(true) },
        )

        commonViewModel.stateFlow.observeBoot {
            if (it) enableReceiver(BootCompleteReceiver::class.java)
            else disableReceiver(BootCompleteReceiver::class.java)
        }.launchIn(lifecycleScope)

        commonViewModel.stateFlow.observeTimeZone {
            if (it) enableReceiver(TimeZoneChangedReceiver::class.java)
            else disableReceiver(TimeZoneChangedReceiver::class.java)
        }.launchIn(lifecycleScope)

        commonViewModel.stateFlow.observeBluetoothHeadset {
            if (it) enableReceiver(BluetoothHeadsetChangeReceiver::class.java)
            else disableReceiver(BluetoothHeadsetChangeReceiver::class.java)
        }.launchIn(lifecycleScope)
    }

    private fun handleCommonContextRegistrations() {
        commonViewModel.stateFlow.observeAirPlane(
            onRegistrationChanged = {
                val ctx = it.context.resolve(activityCtx, appCtx)
                unregister(airPlaneReceiver)
                ctx.registerAirPlane(airPlaneReceiver)
            },
            onUnregistration = {
                val ctx = it.context.resolve(activityCtx, appCtx)
                unregister(airPlaneReceiver)
            }
        ).launchIn(lifecycleScope)

        commonViewModel.stateFlow.observeInputMethod(
            onRegistrationChanged = {
                val ctx = it.context.resolve(activityCtx, appCtx)
                unregister(inputMethodReceiver)
                ctx.registerInputMethod(inputMethodReceiver)
            },
            onUnregistration = {
                val ctx = it.context.resolve(activityCtx, appCtx)
                unregister(inputMethodReceiver)
            }
        ).launchIn(lifecycleScope)

        commonViewModel.stateFlow.observePowerConnection(
            onRegistrationChanged = {
                val ctx = it.context.resolve(activityCtx, appCtx)
                unregister(powerConnectionReceiver)
                ctx.registerPowerConnection(powerConnectionReceiver)
            },
            onUnregistration = {
                val ctx = it.context.resolve(activityCtx, appCtx)
                unregister(powerConnectionReceiver)
            }
        ).launchIn(lifecycleScope)
    }


    private fun handleCustomManifestEnabled() {
        fun updateManifestEnableds(state: ManifestEnableds) {
            customViewModel.handleManifestPageEvent(OnEnabledsChange(state))
        }

        fun updateEnabled0(state: Boolean) {
            val enabled0 = customViewModel.state.enabled0.copy(enabled = state)
            val enableds = customViewModel.state.enableds.copy(enabled0 = enabled0)
            updateManifestEnableds(enableds)
        }

        fun updateEnabled1(state: Boolean) {
            val enabled1 = customViewModel.state.enabled1.copy(enabled = state)
            val enableds = customViewModel.state.enableds.copy(enabled1 = enabled1)
            updateManifestEnableds(enableds)
        }

        fun updateEnabled2(state: Boolean) {
            val enabled2 = customViewModel.state.enabled2.copy(enabled = state)
            val enableds = customViewModel.state.enableds.copy(enabled2 = enabled2)
            updateManifestEnableds(enableds)
        }

        getEnableReceiver(
            receiver = ManifestReceiver0::class.java,
            default = { updateEnabled0(false) },
            disable = { updateEnabled0(false) },
            enable = { updateEnabled0(true) }
        )

        getEnableReceiver(
            receiver = ManifestReceiver1::class.java,
            default = { updateEnabled1(false) },
            disable = { updateEnabled1(false) },
            enable = { updateEnabled1(true) }
        )

        getEnableReceiver(
            receiver = ManifestReceiver2::class.java,
            default = { updateEnabled2(false) },
            disable = { updateEnabled2(false) },
            enable = { updateEnabled2(true) }
        )

        customViewModel.stateFlow.observeManifest0(
            onEnabled = { enableReceiver(ManifestReceiver0::class.java) },
            onDisabled = { disableReceiver(ManifestReceiver0::class.java) }
        ).launchIn(lifecycleScope)

        customViewModel.stateFlow.observeManifest1(
            onEnabled = { enableReceiver(ManifestReceiver1::class.java) },
            onDisabled = { disableReceiver(ManifestReceiver1::class.java) }
        ).launchIn(lifecycleScope)

        customViewModel.stateFlow.observeManifest2(
            onEnabled = { enableReceiver(ManifestReceiver2::class.java) },
            onDisabled = { disableReceiver(ManifestReceiver2::class.java) }
        ).launchIn(lifecycleScope)
    }

    private fun handleCustomContextRegistrations() {
        customViewModel.stateFlow.observeCtx0(
            onRegistrationChanged = {
                val ctx = it.context.resolve(activityCtx, appCtx)
                unregister(custom0)
                ctx.registerReceiver(custom0, it.permission)
            },
            onUnregistration = { unregister(custom0) }
        ).launchIn(lifecycleScope)

        customViewModel.stateFlow.observeCtx1(
            onRegistrationChanged = {
                val ctx = it.context.resolve(activityCtx, appCtx)
                unregister(custom1)
                ctx.registerReceiver(custom1, it.permission)
            },
            onUnregistration = { unregister(custom1) }
        ).launchIn(lifecycleScope)

        customViewModel.stateFlow.observeCtx2(
            onRegistrationChanged = {
                val ctx = it.context.resolve(activityCtx, appCtx)
                unregister(custom2)
                ctx.registerReceiver(custom2, it.permission)
            },
            onUnregistration = { unregister(custom2) }
        ).launchIn(lifecycleScope)
    }

    private fun unregister(receiver: BroadcastReceiver) {
        try {
            activityCtx.unregisterReceiver(receiver)
        } catch (_: IllegalArgumentException) {
        }
        try {
            appCtx.unregisterReceiver(receiver)
        } catch (_: IllegalArgumentException) {
        }
    }

    private inline fun <reified R : BroadcastReceiver> getEnableReceiver(
        receiver: Class<R>, default: () -> Unit, disable: () -> Unit, enable: () -> Unit
    ) {
        val component = ComponentName(appCtx, receiver)
        when (appCtx.packageManager.getComponentEnabledSetting(component)) {
            COMPONENT_ENABLED_STATE_DEFAULT -> {
                log("default")
                default()
            }
            COMPONENT_ENABLED_STATE_DISABLED -> {
                log("disabled")
                disable()
            }
            COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED -> {
                log("disabled until used")
            }
            COMPONENT_ENABLED_STATE_DISABLED_USER -> {
                log("disabled user")
            }
            COMPONENT_ENABLED_STATE_ENABLED -> {
                log("enabled")
                enable()
            }
        }
    }

    override fun onDestroy() {
        commonViewModel.handleActivityDestroy()
        customViewModel.handleActivityDestroy()
        super.onDestroy()
    }

    private inline fun <reified R : BroadcastReceiver> enableReceiver(receiver: Class<R>) {
        val component = ComponentName(appCtx, receiver)
        appCtx.packageManager.setComponentEnabledSetting(
            component, COMPONENT_ENABLED_STATE_ENABLED, DONT_KILL_APP
        )
    }

    private inline fun <reified R : BroadcastReceiver> disableReceiver(receiver: Class<R>) {
        val component = ComponentName(appCtx, receiver)
        appCtx.packageManager.setComponentEnabledSetting(
            component, COMPONENT_ENABLED_STATE_DISABLED, DONT_KILL_APP
        )
    }

    private fun log(message: Any) {
        Log.d(TAG, message.toString())
    }

    companion object {
        private const val TAG = "MainActivity"

        @SuppressLint("ComposableNaming")
        @Composable
        fun setBarsColors(
            bottom: Color = MaterialTheme.colorScheme.onPrimary,
            status: Color = MaterialTheme.colorScheme.primaryContainer,
        ) {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()

            DisposableEffect(systemUiController, useDarkIcons) {
                systemUiController.setStatusBarColor(status, useDarkIcons)
                systemUiController.setNavigationBarColor(bottom)
                onDispose {}
            }
        }
    }
}
