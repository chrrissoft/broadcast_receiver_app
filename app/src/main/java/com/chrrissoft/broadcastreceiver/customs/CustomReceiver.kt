package com.chrrissoft.broadcastreceiver.customs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.chrrissoft.broadcastreceiver.customs.CommonBehaviorReceiver.Companion.QualifierContextReceiver0
import com.chrrissoft.broadcastreceiver.customs.CommonBehaviorReceiver.Companion.QualifierContextReceiver1
import com.chrrissoft.broadcastreceiver.customs.CommonBehaviorReceiver.Companion.QualifierContextReceiver2
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.Sender
import com.chrrissoft.broadcastreceiver.customs.ui.Permission
import com.chrrissoft.broadcastreceiver.work.ReceiverDataToWork
import com.chrrissoft.broadcastreceiver.work.ReceiverDataToWork.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

abstract class CustomReceiver(
    val title: String,
    val receiver: ReceiverDataToWork,
) : BroadcastReceiver() {

    /**********************  Manifest **********************/

    @AndroidEntryPoint
    class ManifestReceiver0 : CustomReceiver(
        title = "Manifest 0",
        receiver = MANIFEST_0,
    ) {
        @Inject
        @CommonBehaviorReceiver.Companion.QualifierManifestReceiver0
        lateinit var commonBehavior: CommonBehaviorReceiver

        override fun onReceive(context: Context?, intent: Intent?) {
            commonBehavior.onReceive(
                receiver = this,
                context = context,
                intent = intent
            )
        }
    }

    @AndroidEntryPoint
    class ManifestReceiver1 : CustomReceiver(
        title = "Manifest 1",
        receiver = MANIFEST_1,
    ) {
        @Inject
        @CommonBehaviorReceiver.Companion.QualifierManifestReceiver1
        lateinit var commonBehavior: CommonBehaviorReceiver

        override fun onReceive(context: Context?, intent: Intent?) {
            commonBehavior.onReceive(
                receiver = this,
                context = context,
                intent = intent
            )
        }
    }

    @AndroidEntryPoint
    class ManifestReceiver2 : CustomReceiver(
        title = "Manifest 2",
        receiver = MANIFEST_2,
    ) {
        @Inject
        @CommonBehaviorReceiver.Companion.QualifierManifestReceiver2
        lateinit var commonBehavior: CommonBehaviorReceiver

        override fun onReceive(context: Context?, intent: Intent?) {
            commonBehavior.onReceive(
                receiver = this,
                context = context,
                intent = intent
            )
        }
    }


    /**********************  Context **********************/

    @Singleton
    @AndroidEntryPoint
    class ContextReceiver0 @Inject constructor(
        @QualifierContextReceiver0
        private val commonBehavior: CommonBehaviorReceiver,
    ) : CustomReceiver(
        title = "Context 0",
        receiver = CONTEXT_0,
    ) {
        override fun onReceive(context: Context?, intent: Intent?) {
            commonBehavior.onReceive(
                receiver = this,
                context = context,
                intent = intent
            )
        }
    }

    @Singleton
    @AndroidEntryPoint
    class ContextReceiver1 @Inject constructor(
        @QualifierContextReceiver1
        private val commonBehavior: CommonBehaviorReceiver,
    ) : CustomReceiver(
        title = "Context 1",
        receiver = CONTEXT_1,
    ) {
        override fun onReceive(context: Context?, intent: Intent?) {
            commonBehavior.onReceive(
                receiver = this,
                context = context,
                intent = intent
            )
        }
    }

    @Singleton
    @AndroidEntryPoint
    class ContextReceiver2 @Inject constructor(
        @QualifierContextReceiver2
        private val commonBehavior: CommonBehaviorReceiver,
    ) : CustomReceiver(
        title = "Context 2",
        receiver = CONTEXT_2,
    ) {
        override fun onReceive(context: Context?, intent: Intent?) {
            commonBehavior.onReceive(
                receiver = this,
                context = context,
                intent = intent
            )
        }
    }

    companion object {
        /**
         * get the [String] passed receiver by receiver in chain. use in [Bundle.getString]
         * */
        private const val COMMON_BUNDLE_DATA_KEY =
            "com.chrrissoft.broadcastreceiver.COMMON_BUNDLE_DATA_KEY"

        val BroadcastReceiver.getCommonExtra
            get() = run {
                getResultExtras(true).getString(COMMON_BUNDLE_DATA_KEY)
            }

        val setCommonExtra
            get() = run {
                Bundle().apply { putString(COMMON_BUNDLE_DATA_KEY, "common extra") }
            }


        const val CUSTOM_MANIFEST_BROADCASTS =
            "com.chrrissoft.broadcastreceiver.CUSTOM_MANIFEST_BROADCASTS"

        private const val CUSTOM_MANIFEST_BROADCASTS_COMPANION =
            "com.chrrissoft.broadcastreceiver.CUSTOM_MANIFEST_BROADCASTS_COMPANION"

        const val CUSTOM_CONTEXT_BROADCASTS =
            "com.chrrissoft.broadcastreceiver.CUSTOM_CONTEXT_BROADCASTS"

        private const val CUSTOM_CONTEXT_BROADCASTS_COMPANION =
            "com.chrrissoft.broadcastreceiver.CUSTOM_CONTEXT_BROADCASTS_COMPANION"

        private val contextFilter = IntentFilter(CUSTOM_CONTEXT_BROADCASTS)

        private val contextIntent = Intent(CUSTOM_CONTEXT_BROADCASTS)
        private val contextIntentCompanion = Intent(CUSTOM_CONTEXT_BROADCASTS_COMPANION)

        private val manifestIntent = Intent(CUSTOM_MANIFEST_BROADCASTS).apply {
            `package` = "com.chrrissoft.broadcastreceiver"
        }
        private val manifestIntentCompanion = Intent(CUSTOM_MANIFEST_BROADCASTS_COMPANION).apply {
            `package` = "com.chrrissoft.broadcastreceivercompanion"
        }

        fun Context.registerReceiver(receiver: CustomReceiver, permission: Permission) {
            registerReceiver(receiver, contextFilter, permission.manifest, null)
        }

        fun Context.sendManifest(sender: Sender) {
            send(manifestIntent, sender)
        }

        fun Context.sendManifestCompanion(sender: Sender) {
            send(manifestIntentCompanion, sender)
        }

        fun Context.sendContext(sender: Sender) {
            send(contextIntent, sender)
        }

        fun Context.sendContextCompanion(sender: Sender) {
            send(contextIntentCompanion, sender)
        }

        private fun Context.send(action: Intent, sender: Sender) {
            if (sender.order) sendOrderedBroadcast(action, sender.permission.manifest,)
            else sendBroadcast(action, sender.permission.manifest)
        }
    }
}
