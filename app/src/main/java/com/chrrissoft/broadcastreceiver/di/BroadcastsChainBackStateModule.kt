package com.chrrissoft.broadcastreceiver.di

import com.chrrissoft.broadcastreceiver.work.BroadcastsChainBackState
import com.chrrissoft.broadcastreceiver.work.BroadcastsChainBackState.BroadcastsChainBackStateQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow

@Module
@InstallIn(SingletonComponent::class)
object BroadcastsChainBackStateModule {

    @Provides
    @BroadcastsChainBackStateQualifier
    fun provide() : MutableStateFlow<BroadcastsChainBackState> {
        return MutableStateFlow(BroadcastsChainBackState.None)
    }
}
