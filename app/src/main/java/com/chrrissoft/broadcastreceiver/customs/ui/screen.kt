package com.chrrissoft.broadcastreceiver.customs.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Terminal
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.chrrissoft.broadcastreceiver.MainActivity.Companion.setBarsColors
import com.chrrissoft.broadcastreceiver.customs.CustomReceiver.Companion.sendContext
import com.chrrissoft.broadcastreceiver.customs.CustomReceiver.Companion.sendContextCompanion
import com.chrrissoft.broadcastreceiver.customs.CustomReceiver.Companion.sendManifest
import com.chrrissoft.broadcastreceiver.customs.CustomReceiver.Companion.sendManifestCompanion
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenEvents.ContextPageEvent
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenEvents.ManifestPageEvent
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenEvents.ManifestPageEvent.*
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.*
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.ContextPageState.ContextRegistrations
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.ManifestPageState.ManifestEnableds
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.Page.Context
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.Page.Manifest
import com.chrrissoft.broadcastreceiver.ui.components.*
import com.chrrissoft.broadcastreceiver.ui.enterAnimatedVisibilityPage
import com.chrrissoft.broadcastreceiver.ui.theme.navItemBarColors
import com.chrrissoft.broadcastreceiver.ui.theme.topAppBarColors
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenEvents.ContextPageEvent.OnRegistrationsChange as OnCtxRegistrationsChange
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenEvents.ContextPageEvent.OnSenderChange as OnCtxSenderChange1

private const val TAG = "CustomBroadcastsPage"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBroadcastsScreen(
    state: CustomsBroadcastsScreenState,
    onContextEvent: (ContextPageEvent) -> Unit,
    onManifestEvent: (ManifestPageEvent) -> Unit,
    onOpenDrawer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    setBarsColors(bottom = colorScheme.primaryContainer)
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors,
                title = { Text(text = "Custom Broadcasts") },
                navigationIcon = {
                    IconButton(onClick = { onOpenDrawer() }) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = colorScheme.primaryContainer) {
                NavigationBarItem(
                    selected = state.page==Manifest,
                    label = { Text(text = "Manifest") },
                    onClick = { onContextEvent(ContextPageEvent.OnSwitchPage()) },
                    colors = navItemBarColors,
                    icon = {
                        Icon(imageVector = Icons.Rounded.Description, (null))
                    }
                )

                NavigationBarItem(
                    selected = state.page==Context,
                    label = { Text(text = "Context") },
                    onClick = { onManifestEvent(OnSwitchPage()) },
                    colors = navItemBarColors,
                    icon = {
                        Icon(imageVector = Icons.Rounded.Terminal, (null))
                    }
                )
            }
        },
        content = { padding ->
            when (state.page) {
                Context -> {
                    ContextBroadcastsPage(
                        state = state.contextPageState,
                        onEvent = { onContextEvent(it) },
                        modifier = Modifier.padding(padding)
                    )
                }
                Manifest -> {
                    ManifestBroadcastsPage(
                        state = state.manifestPageState,
                        onEvent = { onManifestEvent(it) },
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    )
}

/*****************  manifest ui  *****************/

@Composable
private fun ManifestBroadcastsPage(
    state: ManifestPageState,
    onEvent: (ManifestPageEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.onPrimary)
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val visibleState = remember {
            MutableTransitionState(false).apply {
                this.targetState = true
            }
        }

        AnimatedVisibility(
            visibleState = visibleState,
            enter = enterAnimatedVisibilityPage
        ) {
            Column {
                ManifestEnabled(
                    state = state.enableds,
                    onStateChanged = {
                        onEvent(OnEnabledsChange(it))
                    }
                )

                Divider(color = colorScheme.primary, modifier = Modifier.padding(10.dp))

                val ctx = LocalContext.current
                Sender(
                    state = state.sender,
                    onStateChange = {
                        onEvent(OnSenderChange(it))
                    },
                    onSend = { ctx.sendManifest(state.sender) }
                )

                Divider(color = colorScheme.primary, modifier = Modifier.padding(10.dp))
                Sender(
                    state = state.senderCompanion,
                    onStateChange = {
                        onEvent(OnSenderCompanionChange(it))
                    },
                    onSend = { ctx.sendManifestCompanion(state.senderCompanion) },
                    title = "Sender companion"
                )
            }
        }
    }
}

@Composable
private fun ManifestEnabled(
    state: ManifestEnableds,
    onStateChanged: (ManifestEnableds) -> Unit,
    modifier: Modifier = Modifier,
) {
    Container(
        title = "Enabled Receivers",
        modifier = modifier
    ) {
        Checkbox(
            text = "Receiver 0",
            enabled = !state.enabled0.finish,
            checked = state.enabled0.enabled,
            onCheckedChanged = {
                val receiver0 = state.enabled0.copy(enabled = it)
                onStateChanged(state.copy(enabled0 = receiver0))
            },
            modifier = Modifier.fillMaxWidth()
        )

        Checkbox(
            text = "Receiver 1",
            enabled = !state.enabled1.finish,
            checked = state.enabled1.enabled,
            onCheckedChanged = {
                val receiver1 = state.enabled1.copy(enabled = it)
                onStateChanged(state.copy(enabled1 = receiver1))
            },
            modifier = Modifier.fillMaxWidth()
        )

        Checkbox(
            text = "Receiver 2",
            enabled = !state.enabled2.finish,
            checked = state.enabled2.enabled,
            onCheckedChanged = {
                val receiver2 = state.enabled2.copy(enabled = it)
                onStateChanged(state.copy(enabled2 = receiver2))
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


/*****************  context ui  *****************/

@Composable
private fun ContextBroadcastsPage(
    state: ContextPageState,
    onEvent: (ContextPageEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.onPrimary)
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val visibleState = remember {
            MutableTransitionState(false).apply {
                this.targetState = true
            }
        }

        AnimatedVisibility(
            visibleState = visibleState,
            enter = enterAnimatedVisibilityPage
        ) {
            Column {
                ContextRegistrations(
                    state = state.registrations,
                    onStateChanged = {
                        onEvent(OnCtxRegistrationsChange(it))
                    }
                )

                Divider(color = colorScheme.primary, modifier = Modifier.padding(10.dp))

                val ctx = LocalContext.current
                Sender(
                    state = state.sender,
                    onStateChange = {
                        onEvent(OnCtxSenderChange1(it))
                    },
                    onSend = { ctx.sendContext(state.sender) }
                )

                Divider(color = colorScheme.primary, modifier = Modifier.padding(10.dp))
                Sender(
                    state = state.senderCompanion,
                    onStateChange = {
                        onEvent(ContextPageEvent.OnSenderCompanionChange(it))
                    },
                    onSend = { ctx.sendContextCompanion(state.senderCompanion) },
                    title = "Sender companion"
                )
            }
        }
    }
}

@Composable
private fun ContextRegistrations(
    state: ContextRegistrations,
    onStateChanged: (ContextRegistrations) -> Unit,
    modifier: Modifier = Modifier,
) {
    Container(
        title = "Registered Receivers",
        modifier = modifier
    ) {
        ContextRegistration(
            title = "Receiver 0",
            state = state.registration0,
            onStateChanged = {
                onStateChanged(state.copy(registration0 = it))
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        ContextRegistration(
            title = "Receiver 1",
            state = state.registration1,
            onStateChanged = {
                onStateChanged(state.copy(registration1 = it))
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        ContextRegistration(
            title = "Receiver 2",
            state = state.registration2,
            onStateChanged = {
                onStateChanged(state.copy(registration2 = it))
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}


@Composable
private fun Sender(
    state: Sender,
    onStateChange: (Sender) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "Sender",
) {
    Container(title = title, modifier = modifier) {
        Checkbox(
            text = "Send order broadcast",
            enabled = state.enabled,
            checked = state.order,
            onCheckedChanged = {
                onStateChange(state.copy(order = it))
            },
            modifier = Modifier.fillMaxWidth()
        )

        Permission(
            enabled = state.enabled,
            permission = state.permission,
            onPermissionChanged = {
                onStateChange(state.copy(permission = it))
            },
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        SenderButton(enabled = state.enabled) { onSend() }
    }
}
