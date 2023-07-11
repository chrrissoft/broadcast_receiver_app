package com.chrrissoft.broadcastreceiver.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WorkManagerModule {
    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext app: Context): WorkManager {
        return WorkManager.getInstance(app)
    }
}
