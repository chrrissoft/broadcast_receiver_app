package com.chrrissoft.broadcastreceiver.di

import com.chrrissoft.broadcastreceiver.work.BroadcastsChainBack
import com.chrrissoft.broadcastreceiver.work.BroadcastsChainBackImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkerDependenciesModule {
    @Binds
    abstract fun provideDependencies(
        impl: BroadcastsChainBackImpl
    ): BroadcastsChainBack
}
