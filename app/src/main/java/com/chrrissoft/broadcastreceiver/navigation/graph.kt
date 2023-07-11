package com.chrrissoft.broadcastreceiver.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Podcasts
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chrrissoft.broadcastreceiver.common.ui.CommonBroadcastsScreen
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenEvent
import com.chrrissoft.broadcastreceiver.common.ui.CommonsBroadcastsScreenState
import com.chrrissoft.broadcastreceiver.customs.ui.CustomBroadcastsScreen
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenEvents
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState
import com.chrrissoft.broadcastreceiver.navigation.Screens.COMMON_SCREEN
import com.chrrissoft.broadcastreceiver.navigation.Screens.CUSTOM_SCREEN
import com.chrrissoft.broadcastreceiver.ui.theme.navigationDrawerItemColors
import kotlinx.coroutines.launch

@Composable
fun Graph(
    customScreenState: CustomsBroadcastsScreenState,
    onCustomContextPageEvent: (CustomsBroadcastsScreenEvents.ContextPageEvent) -> Unit,
    onCustomManifestPageEvent: (CustomsBroadcastsScreenEvents.ManifestPageEvent) -> Unit,

    commonScreenState: CommonsBroadcastsScreenState,
    onCommonScreenEvent: (CommonsBroadcastsScreenEvent) -> Unit,
) {
    val controller = rememberNavController()

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val currentScreen by controller.currentBackStack.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = colorScheme.primaryContainer
            ) {
                Column(
                    horizontalAlignment = CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Podcasts,
                        contentDescription = null,
                        tint = colorScheme.primary,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(vertical = 15.dp)
                    )

                    Text(
                        text = "Broadcast Receiver App",
                        style = typography.headlineMedium,
                        color = colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 20.dp),
                    color = colorScheme.primary
                )

                NavigationDrawerItem(
                    colors = navigationDrawerItemColors,
                    label = { Text(text = "Commons Broadcasts") },
                    icon = {
                        Icon(imageVector = Icons.Rounded.Info,
                            contentDescription = null)
                    },
                    selected = currentScreen.lastOrNull()?.destination?.route==COMMON_SCREEN,
                    onClick = {
                        controller.navigate(COMMON_SCREEN) {
                            launchSingleTop = true
                            popUpTo(COMMON_SCREEN) {
                                inclusive = true
                            }
                        }
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .padding(horizontal = 10.dp)
                )

                NavigationDrawerItem(
                    colors = navigationDrawerItemColors,
                    label = { Text(text = "Custom Broadcasts") },
                    icon = {
                        Icon(imageVector = Icons.Rounded.Tune,
                            contentDescription = null)
                    },
                    selected = currentScreen.lastOrNull()?.destination?.route==CUSTOM_SCREEN,
                    onClick = {
                        controller.navigate(CUSTOM_SCREEN) {
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        }
    ) {
        NavHost(controller, COMMON_SCREEN) {
            composable(COMMON_SCREEN) {
                CommonBroadcastsScreen(
                    state = commonScreenState,
                    onEvent = onCommonScreenEvent,
                    onOpenDrawer = {
                        scope.launch { drawerState.open() }
                    }
                )
            }

            composable(CUSTOM_SCREEN) {
                CustomBroadcastsScreen(
                    state = customScreenState,
                    onContextEvent = onCustomContextPageEvent,
                    onManifestEvent = onCustomManifestPageEvent,
                    onOpenDrawer = {
                        scope.launch { drawerState.open() }
                    }
                )
            }
        }
    }
}
