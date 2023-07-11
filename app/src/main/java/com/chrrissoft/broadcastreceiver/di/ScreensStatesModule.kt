package com.chrrissoft.broadcastreceiver.di

import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScreensStatesModule {
    @Provides
    @Singleton
    fun provideCustomScreenState(): MutableStateFlow<CustomsBroadcastsScreenState> =
        MutableStateFlow(CustomsBroadcastsScreenState())

    @Provides
    @Singleton
    fun provideCommonScreenState(): MutableStateFlow<CommonsBroadcastsScreenState> =
        MutableStateFlow(CommonsBroadcastsScreenState())
}
