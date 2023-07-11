package com.chrrissoft.broadcastreceiver

import android.content.Context

enum class CtxRegistration {
    Activity, Application;

    fun resolve(activity: MainActivity, app: Context): Context {
        return when (this) {
            Activity -> activity
            Application -> app
        }
    }
}
