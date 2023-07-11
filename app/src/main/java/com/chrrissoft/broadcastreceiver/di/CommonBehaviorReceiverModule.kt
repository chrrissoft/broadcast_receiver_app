package com.chrrissoft.broadcastreceiver.di

import androidx.work.WorkManager
import com.chrrissoft.broadcastreceiver.CustomNotificationManager
import com.chrrissoft.broadcastreceiver.customs.CommonBehaviorReceiver
import com.chrrissoft.broadcastreceiver.customs.ReceiverResState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

@Module
@InstallIn(SingletonComponent::class)
object CommonBehaviorReceiverModule {
    @Provides
    @CommonBehaviorReceiver.Companion.QualifierContextReceiver0
    fun provideContext0(
        @ReceiverResState.ContextReceiverResState0
        state: MutableStateFlow<ReceiverResState>,
        notificationManager: CustomNotificationManager,
        scope: CoroutineScope,
        workManager: WorkManager
    ): CommonBehaviorReceiver = CommonBehaviorReceiver(
        state = state,
        notificationManager = notificationManager,
        scope = scope,
        workManager = workManager
    )


    @Provides
    @CommonBehaviorReceiver.Companion.QualifierContextReceiver1
    fun provideContext1(
        @ReceiverResState.ContextReceiverResState1
        state: MutableStateFlow<ReceiverResState>,
        notificationManager: CustomNotificationManager,
        scope: CoroutineScope,
        workManager: WorkManager
    ): CommonBehaviorReceiver = CommonBehaviorReceiver(
        state = state,
        notificationManager = notificationManager,
        scope = scope,
        workManager = workManager
    )

    @Provides
    @CommonBehaviorReceiver.Companion.QualifierContextReceiver2
    fun provideContext2(
        @ReceiverResState.ContextReceiverResState2
        state: MutableStateFlow<ReceiverResState>,
        notificationManager: CustomNotificationManager,
        scope: CoroutineScope,
        workManager: WorkManager
    ): CommonBehaviorReceiver = CommonBehaviorReceiver(
        state = state,
        notificationManager = notificationManager,
        scope = scope,
        workManager = workManager
    )



    @Provides
    @CommonBehaviorReceiver.Companion.QualifierManifestReceiver0
    fun provideManifest0(
        @ReceiverResState.ManifestReceiverResState0
        state: MutableStateFlow<ReceiverResState>,
        notificationManager: CustomNotificationManager,
        scope: CoroutineScope,
        workManager: WorkManager
    ): CommonBehaviorReceiver = CommonBehaviorReceiver(
        state = state,
        notificationManager = notificationManager,
        scope = scope,
        workManager = workManager
    )


    @Provides
    @CommonBehaviorReceiver.Companion.QualifierManifestReceiver1
    fun provideManifest1(
        @ReceiverResState.ManifestReceiverResState1
        state: MutableStateFlow<ReceiverResState>,
        notificationManager: CustomNotificationManager,
        scope: CoroutineScope,
        workManager: WorkManager
    ): CommonBehaviorReceiver = CommonBehaviorReceiver(
        state = state,
        notificationManager = notificationManager,
        scope = scope,
        workManager = workManager
    )

    @Provides
    @CommonBehaviorReceiver.Companion.QualifierManifestReceiver2
    fun provideManifest2(
        @ReceiverResState.ManifestReceiverResState2
        state: MutableStateFlow<ReceiverResState>,
        notificationManager: CustomNotificationManager,
        scope: CoroutineScope,
        workManager: WorkManager
    ): CommonBehaviorReceiver = CommonBehaviorReceiver(
        state = state,
        notificationManager = notificationManager,
        scope = scope,
        workManager = workManager
    )
}
