package com.chrrissoft.broadcastreceiver.di

import com.chrrissoft.broadcastreceiver.customs.ReceiverResState
import com.chrrissoft.broadcastreceiver.customs.ReceiverResState.ContextReceiverResState0
import com.chrrissoft.broadcastreceiver.customs.ReceiverResState.None
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReceiverResStateModule {
    @Provides
    @Singleton
    @ContextReceiverResState0
    fun provideContext0(): MutableStateFlow<ReceiverResState> {
        return MutableStateFlow(None)
    }

    @Provides
    @Singleton
    @ReceiverResState.ContextReceiverResState1
    fun provideContext1(): MutableStateFlow<ReceiverResState> {
        return MutableStateFlow(None)
    }

    @Provides
    @Singleton
    @ReceiverResState.ContextReceiverResState2
    fun provideContext2(): MutableStateFlow<ReceiverResState> {
        return MutableStateFlow(None)
    }


    @Provides
    @Singleton
    @ReceiverResState.ManifestReceiverResState0
    fun provideManifest0(): MutableStateFlow<ReceiverResState> {
        return MutableStateFlow(None)
    }

    @Provides
    @Singleton
    @ReceiverResState.ManifestReceiverResState1
    fun provideManifest1(): MutableStateFlow<ReceiverResState> {
        return MutableStateFlow(None)
    }

    @Provides
    @Singleton
    @ReceiverResState.ManifestReceiverResState2
    fun provideManifest2(): MutableStateFlow<ReceiverResState> {
        return MutableStateFlow(None)
    }
}
