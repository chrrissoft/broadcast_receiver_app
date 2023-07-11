package com.chrrissoft.broadcastreceiver.di

import android.content.Context
import com.chrrissoft.broadcastreceiver.BroadcastReceiverApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideScope(
        @ApplicationContext app: Context
    ): CoroutineScope = (app as BroadcastReceiverApp).scope

    @Provides
    fun provideApp(@ApplicationContext app: Context): BroadcastReceiverApp {
        return app as BroadcastReceiverApp
    }
}