package com.chrrissoft.broadcastreceiver.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.IntentFilter
import android.util.Log
import com.chrrissoft.broadcastreceiver.CustomNotificationManager
import com.chrrissoft.broadcastreceiver.CustomNotificationManager.Channels.RECEIVER_CHANNEL_ID
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
abstract class CommonReceiver(private val title: String) : BroadcastReceiver() {
    @Inject
    lateinit var notificationManager: CustomNotificationManager

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "CommonReceiver: onReceive")
        notificationManager.sendNotification(title, RECEIVER_CHANNEL_ID)
    }

    /*****************  manifest registered  *****************/

    class TimeZoneChangedReceiver
        : CommonReceiver(title = "Time zone changed")

    class BluetoothHeadsetChangeReceiver
        : CommonReceiver(title = "BluetoothHeadsetChangeReceiver")

    class BootCompleteReceiver
        : CommonReceiver(title = "BootCompleteReceiver")


    /*****************  context registered  *****************/

    @Singleton
    class AirPlaneModeChangedReceiver @Inject constructor() :
        CommonReceiver(title = "AirPlaneModeChangedReceiver") {
        companion object {
            private val filter = IntentFilter(ACTION_AIRPLANE_MODE_CHANGED)

            fun Context.registerAirPlane(
                receiver: AirPlaneModeChangedReceiver
            ) {
                registerReceiver(receiver, filter)
            }
        }
    }

    @Singleton
    class PowerConnectionReceiver @Inject constructor() :
        CommonReceiver(title = "PowerConnectionReceiver") {
        companion object {
            private val filter = IntentFilter(ACTION_POWER_DISCONNECTED).apply {
                addAction(ACTION_POWER_CONNECTED)
            }

            fun Context.registerPowerConnection(
                receiver: PowerConnectionReceiver
            ) {
                registerReceiver(receiver, filter)
            }
        }
    }

    @Singleton
    class InputMethodChangedReceiver @Inject constructor() :
        CommonReceiver(title = "InputMethodChangedReceiver") {
        companion object {
            private val filter = IntentFilter(ACTION_INPUT_METHOD_CHANGED)

            fun Context.registerInputMethod(
                receiver: InputMethodChangedReceiver
            ) {
                registerReceiver(receiver, filter)
            }
        }
    }


    companion object {
        private const val TAG = "CommonReceiver"
    }
}
