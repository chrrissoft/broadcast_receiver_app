package com.chrrissoft.broadcastreceiver.work

import kotlinx.coroutines.flow.StateFlow

interface BroadcastsChainBack {
    val state: StateFlow<BroadcastsChainBackState>

    fun getResult(receiver: ReceiverDataToWork) : BroadcastsChainBackState

    fun onCancelWork(receiver: ReceiverDataToWork)
}
