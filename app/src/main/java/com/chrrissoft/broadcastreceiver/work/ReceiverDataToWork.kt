package com.chrrissoft.broadcastreceiver.work

import androidx.work.Data
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
enum class ReceiverDataToWork {
    CONTEXT_0,
    CONTEXT_1,
    CONTEXT_2,

    MANIFEST_0,
    MANIFEST_1,
    MANIFEST_2;

    companion object {
        private const val RECEIVER_KEY = "RECEIVER_KEY"

        fun Data.Builder.setReceiver(receiver: ReceiverDataToWork): Data.Builder {
            return putString(RECEIVER_KEY, Json.encodeToString(receiver))
        }

        val Data.getReceiver
            get() = run {
                Json.decodeFromString<ReceiverDataToWork>(
                    getString(RECEIVER_KEY) ?: throw IllegalArgumentException()
                )
            }

    }
}
