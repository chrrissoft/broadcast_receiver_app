package com.chrrissoft.broadcastreceiver.customs.ui

import android.Manifest

sealed interface Permission {

    val manifest: String?

    enum class With : Permission {
        Matches {
            override val manifest = Manifest.permission.BLUETOOTH
        },
        NotMatches {
            override val manifest = Manifest.permission.CAMERA
        }
    }

    object WithOut : Permission {
        override val manifest: String? = null
    }
}
