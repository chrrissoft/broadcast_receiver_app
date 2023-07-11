package com.chrrissoft.broadcastreceiver.work

import javax.inject.Qualifier

sealed interface BroadcastsChainBackState {
    object None : BroadcastsChainBackState

    object Success : BroadcastsChainBackState

    object Failure : BroadcastsChainBackState

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BroadcastsChainBackStateQualifier

}
