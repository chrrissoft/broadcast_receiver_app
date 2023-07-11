package com.chrrissoft.broadcastreceiver.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.broadcastreceiver.ui.theme.BroadcastReceiverTheme


@Composable
fun AppUiContainer(content: @Composable () -> Unit) {
    BroadcastReceiverTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}
