package com.chrrissoft.broadcastreceiver.customs

import android.os.Bundle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import javax.inject.Qualifier

sealed interface ReceiverResState {
    object None : ReceiverResState

    data class Success(
        val code: Int,
        val data: String,
        val extra: Bundle,
    ) : ReceiverResState

    data class Failure(
        val exception: Exception? = null,
        val cause: Throwable? = null,
    ) : ReceiverResState


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ContextReceiverResState0

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ContextReceiverResState1

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ContextReceiverResState2



    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ManifestReceiverResState0

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ManifestReceiverResState1

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ManifestReceiverResState2

    companion object {
        fun StateFlow<ReceiverResState>.onComplete(block: suspend () -> Unit): Flow<ReceiverResState> {
            return onEach { if (it is Failure || it is Success) block() }
        }
    }
}
