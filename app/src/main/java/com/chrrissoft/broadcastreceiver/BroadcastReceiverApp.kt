package com.chrrissoft.broadcastreceiver

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Configuration.Provider
import com.chrrissoft.broadcastreceiver.customs.ReceiverResState
import com.chrrissoft.broadcastreceiver.customs.ReceiverResState.Companion.onComplete
import com.chrrissoft.broadcastreceiver.customs.ReceiverResState.Failure
import com.chrrissoft.broadcastreceiver.customs.ReceiverResState.Success
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.ContextPageState.ContextRegistrations
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.ContextPageState.ContextRegistrations.ContextRegistration
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.ManifestPageState.ManifestEnableds
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.ManifestPageState.ManifestEnableds.ManifestEnabled
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltAndroidApp
class BroadcastReceiverApp : Application(), Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()


    @Inject
    @ReceiverResState.ManifestReceiverResState0
    lateinit var manifestState0: MutableStateFlow<ReceiverResState>

    @Inject
    @ReceiverResState.ManifestReceiverResState1
    lateinit var manifestState1: MutableStateFlow<ReceiverResState>

    @Inject
    @ReceiverResState.ManifestReceiverResState2
    lateinit var manifestState2: MutableStateFlow<ReceiverResState>


    @Inject
    @ReceiverResState.ContextReceiverResState0
    lateinit var contextState0: MutableStateFlow<ReceiverResState>

    @Inject
    @ReceiverResState.ContextReceiverResState1
    lateinit var contextState1: MutableStateFlow<ReceiverResState>

    @Inject
    @ReceiverResState.ContextReceiverResState2
    lateinit var contextState2: MutableStateFlow<ReceiverResState>

    @Inject
    lateinit var screenState: MutableStateFlow<CustomsBroadcastsScreenState>

    val scope = CoroutineScope(Job())

    var initAlready = false

    fun init() {
        if (initAlready) return
        initAlready = true
        observeManifestState()
        observeContextState()
    }

    private fun observeManifestState() {
        manifestState0
            .onComplete {
                screenState.value.enableds.copy(
                    enabled0 = ManifestEnabled((false))
                ).apply { resetManifestRegistration((this)) }
            }
            .launchIn(scope)

        manifestState1
            .onComplete {
                screenState.value.enableds.copy(
                    enabled1 = ManifestEnabled((false))
                ).apply { resetManifestRegistration((this)) }
            }
            .launchIn(scope)

        manifestState2
            .onComplete {
                screenState.value.enableds.copy(
                    enabled2 = ManifestEnabled((false))
                ).apply { resetManifestRegistration((this)) }
            }
            .launchIn(scope)

        onAllComplete(manifestState0, manifestState1, manifestState2) {
            screenState.update {
                val sender = it.manifestSender.copy(enabled = false)
                val manifest = it.manifestPageState.copy(sender = sender)
                it.copy(manifestPageState = manifest)
            }
        }.launchIn(scope)
    }

    private fun observeContextState() {
        contextState0
            .onComplete {
                screenState.value.registrations.copy(
                    registration0 = ContextRegistration(enabled = false)
                ).apply { resetContextRegistration((this)) }
            }
            .launchIn(scope)

        contextState1
            .onComplete {
                screenState.value.registrations.copy(
                    registration1 = ContextRegistration(enabled = false)
                ).apply { resetContextRegistration((this)) }
            }
            .launchIn(scope)

        contextState2
            .onComplete {
                screenState.value.registrations.copy(
                    registration2 = ContextRegistration(enabled = false)
                ).apply { resetContextRegistration((this)) }
            }
            .launchIn(scope)

        onAllComplete(contextState0, contextState1, contextState2) {
            screenState.update {
                val sender = it.contextSender.copy(enabled = false)
                val manifest = it.contextPageState.copy(sender = sender)
                it.copy(contextPageState = manifest)
            }
        }.launchIn(scope)
    }

    private fun onAllComplete(
        f0: Flow<ReceiverResState>,
        f1: Flow<ReceiverResState>,
        f2: Flow<ReceiverResState>,
        block: () -> Unit
    ) : Flow<Unit> {
        return combineTransform(f0, f1, f2) { s0, s1, s2 ->
            val s0Finish = s0 is Success || s0 is Failure
            val s1Finish = s1 is Success || s1 is Failure
            val s2Finish = s2 is Success || s2 is Failure
            if (s0Finish && s1Finish && s2Finish) emit(Unit)
        }
            .onEach { block() }
    }

    private fun resetManifestRegistration(
        registrations: ManifestEnableds,
    ) {
        screenState.update {
            val page = it.manifestPageState.copy(enableds = registrations)
            it.copy(manifestPageState = page)
        }
    }

    private fun resetContextRegistration(
        registrations: ContextRegistrations,
    ) {
        screenState.update {
            val page = it.contextPageState.copy(registrations = registrations)
            it.copy(contextPageState = page)
        }
    }

}
