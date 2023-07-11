package com.chrrissoft.broadcastreceiver.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chrrissoft.broadcastreceiver.MainActivity.Companion.setBarsColors
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenEvent.OnChangeContextPageState
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenEvent.OnChangeManifestPageState
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState.ContextState
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState.ManifestState
import com.chrrissoft.broadcastreceiver.ui.components.Checkbox
import com.chrrissoft.broadcastreceiver.ui.components.Container
import com.chrrissoft.broadcastreceiver.ui.components.ContextRegistration
import com.chrrissoft.broadcastreceiver.ui.enterAnimatedVisibilityPage
import com.chrrissoft.broadcastreceiver.ui.theme.topAppBarColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonBroadcastsScreen(
    state: CommonsBroadcastsScreenState,
    onEvent: (CommonsBroadcastsScreenEvent) -> Unit,
    onOpenDrawer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    setBarsColors()
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors,
                navigationIcon = {
                    IconButton(onClick = { onOpenDrawer() }) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = null
                        )
                    }
                },
                title = { Text(text = "Common Broadcasts") }
            )
        },
        content = { padding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(colorScheme.onPrimary)
                    .padding(10.dp)
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
                        ManifestBroadcasts(
                            state = state.manifestState,
                            onEvent = { onEvent(it) },
                        )

                        Divider(
                            color = colorScheme.primary,
                            modifier = Modifier.padding(20.dp)
                        )

                        ContextBroadcasts(
                            state = state.contextState,
                            onEvent = { onEvent(it) },
                        )
                    }
                }
            }
        }
    )
}

/*****************  manifest ui  *****************/

@Composable
private fun ManifestBroadcasts(
    state: ManifestState,
    onEvent: (OnChangeManifestPageState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Container(
        title = "Manifest Enabled",
        modifier = modifier
    ) {
        Checkbox(
            text = "Boot Complete",
            enabled = true,
            checked = state.bootComplete,
            onCheckedChanged = {
                onEvent(OnChangeManifestPageState(state.copy(bootComplete = it)))
            },
            modifier = Modifier.fillMaxWidth()
        )

        Checkbox(
            text = "Time zone",
            enabled = true,
            checked = state.timeZone,
            onCheckedChanged = {
                onEvent(OnChangeManifestPageState(state.copy(timeZone = it)))
            },
            modifier = Modifier.fillMaxWidth()
        )

        Checkbox(
            text = "Bluetooth Headset",
            enabled = true,
            checked = state.bluetoothHeadset,
            onCheckedChanged = {
                onEvent(OnChangeManifestPageState(state.copy(bluetoothHeadset = it)))
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}



/*****************  context ui  *****************/

@Composable
private fun ContextBroadcasts(
    state: ContextState,
    onEvent: (OnChangeContextPageState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Container(
        title = "Context Register",
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp)
        ) {
            Checkbox(
                text = "Air plane",
                enabled = true,
                checked = state.airPlane.registered,
                onCheckedChanged = {
                    val new = state.airPlane.copy(registered = it)
                    onEvent(OnChangeContextPageState(state.copy(airPlane = new)))
                },
            )
            ContextRegistration(
                enabled = state.airPlane.registered,
                context = state.airPlane.context,
                onContextChanged = {
                    val new = state.airPlane.copy(context = it)
                    onEvent(OnChangeContextPageState(state.copy(airPlane = new)))
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp)
        ) {
            Checkbox(
                text = "Input method",
                enabled = true,
                checked = state.inputMethod.registered,
                onCheckedChanged = {
                    val new = state.inputMethod.copy(registered = it)
                    onEvent(OnChangeContextPageState(state.copy(inputMethod = new)))
                },
            )
            ContextRegistration(
                enabled = state.inputMethod.registered,
                context = state.inputMethod.context,
                onContextChanged = {
                    val new = state.inputMethod.copy(context = it)
                    onEvent(OnChangeContextPageState(state.copy(inputMethod = new)))
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp)
        ) {
            Checkbox(
                text = "Power connection",
                enabled = true,
                checked = state.powerConnection.registered,
                onCheckedChanged = {
                    val new = state.powerConnection.copy(registered = it)
                    onEvent(OnChangeContextPageState(state.copy(powerConnection = new)))
                },
            )
            ContextRegistration(
                enabled = state.powerConnection.registered,
                context = state.powerConnection.context,
                onContextChanged = {
                    val new = state.powerConnection.copy(context = it)
                    onEvent(OnChangeContextPageState(state.copy(powerConnection = new)))
                }
            )
        }
    }
}

