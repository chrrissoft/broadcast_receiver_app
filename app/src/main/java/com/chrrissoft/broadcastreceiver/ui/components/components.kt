package com.chrrissoft.broadcastreceiver.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bluetooth
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.HideSource
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import com.chrrissoft.broadcastreceiver.CtxRegistration
import com.chrrissoft.broadcastreceiver.CtxRegistration.Activity
import com.chrrissoft.broadcastreceiver.CtxRegistration.Application
import com.chrrissoft.broadcastreceiver.customs.ui.CustomsBroadcastsScreenState.ContextPageState.ContextRegistrations.ContextRegistration
import com.chrrissoft.broadcastreceiver.customs.ui.Permission
import com.chrrissoft.broadcastreceiver.customs.ui.Permission.With.Matches
import com.chrrissoft.broadcastreceiver.customs.ui.Permission.With.NotMatches
import com.chrrissoft.broadcastreceiver.customs.ui.Permission.WithOut
import com.chrrissoft.broadcastreceiver.ui.theme.cardColors
import com.chrrissoft.broadcastreceiver.ui.theme.checkboxColors
import com.chrrissoft.broadcastreceiver.ui.theme.inputChipBorderColor
import com.chrrissoft.broadcastreceiver.ui.theme.inputChipColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Permission(
    enabled: Boolean,
    permission: Permission,
    onPermissionChanged: (Permission) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically,
        horizontalArrangement = SpaceBetween
    ) {
        InputChip(
            enabled = enabled,
            colors = inputChipColors,
            selected = permission==WithOut,
            onClick = { onPermissionChanged(WithOut) },
            label = { Text(style = typography.labelMedium, text = "With out") },
            trailingIcon = {
                Icon(
                    contentDescription = null,
                    imageVector = Icons.Rounded.HideSource,
                )
            },
            border = inputChipBorderColor
        )

        InputChip(
            enabled = enabled,
            colors = inputChipColors,
            selected = permission==Matches,
            onClick = { onPermissionChanged(Matches) },
            label = { Text(style = typography.labelMedium, text = "Matches") },
            trailingIcon = {
                Icon(
                    contentDescription = null,
                    imageVector = Icons.Rounded.Bluetooth,
                )
            },
            border = inputChipBorderColor
        )

        InputChip(
            enabled = enabled,
            colors = inputChipColors,
            selected = permission==NotMatches,
            onClick = { onPermissionChanged(NotMatches) },
            label = { Text(style = typography.labelMedium, text = "NotMatches") },
            trailingIcon = {
                Icon(
                    contentDescription = null,
                    imageVector = Icons.Rounded.Camera,
                )
            },
            border = inputChipBorderColor
        )
    }
}

@Composable
fun ContextRegistration(
    enabled: Boolean,
    context: CtxRegistration,
    onContextChanged: (CtxRegistration) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = SpaceBetween,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .clickable(enabled) { onContextChanged(Activity) }
        ) {
            val textColor =
                if (context==Activity && enabled) colorScheme.primary
                else if (context!=Activity && enabled) colorScheme.secondary
                else colorScheme.secondary.copy(.5f)
            RadioButton(
                enabled = enabled,
                selected = context==Activity,
                onClick = { onContextChanged(Activity) }
            )
            Text(
                text = "Activity",
                style = typography.labelMedium.copy(color = textColor)
            )
        }

        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .clickable(enabled) { onContextChanged(Application) }
        ) {
            val textColor =
                if (context==Application && enabled) colorScheme.primary
                else if (context!=Application && enabled) colorScheme.secondary
                else colorScheme.secondary.copy(.5f)
            RadioButton(
                enabled = enabled,
                selected = context==Application,
                onClick = { onContextChanged(Application) }
            )
            Text(
                text = "App",
                style = typography.labelMedium.copy(color = textColor)
            )
        }
    }
}

@Composable
fun SenderButton(
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onSend: () -> Unit,
) {
    Button(
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onPrimary,
            disabledContainerColor = colorScheme.onPrimary.copy(.5f),
            disabledContentColor = colorScheme.secondary.copy(.5f),
        ),
        onClick = { onSend() },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(bottom = 10.dp)
    ) {
        Text(text = "Send Broadcast")
    }
}

@Composable
fun Checkbox(
    text: String,
    enabled: Boolean,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = CenterVertically,
        modifier = modifier
            .clip(shapes.medium)
            .clickable(enabled) { onCheckedChanged(!checked) }
    ) {
        Checkbox(
            checked = checked,
            enabled = enabled,
            colors = checkboxColors,
            onCheckedChange = onCheckedChanged,
        )
        val textColor =
            if (checked && enabled) colorScheme.primary
            else if (!checked && enabled) colorScheme.secondary
            else colorScheme.secondary.copy(.5f)
        Text(text = text, color = textColor, style = typography.labelMedium)
    }
}

@Composable
fun Container(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        colors = cardColors,
    ) {
        Text(
            text = title,
            textAlign = Center,
            color = colorScheme.primary,
            style = typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )

        Divider(
            color = colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp)
        )

        content()
    }
}

@Composable
fun ContextRegistration(
    title: String,
    state: ContextRegistration,
    onStateChanged: (ContextRegistration) -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor = if (state.registered && state.enabled) colorScheme.primary
    else if (!state.registered && state.enabled) colorScheme.secondary
    else colorScheme.secondary.copy(.5f)
    Column(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .clip(shapes.medium)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shapes.medium
            )
    ) {
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp)
        ) {
            Checkbox(
                text = title,
                enabled = state.enabled,
                checked = state.registered,
                onCheckedChanged = {
                    onStateChanged(state.copy(registered = it))
                },
            )
            ContextRegistration(
                enabled = state.enabled && state.registered,
                context = state.context,
                onContextChanged = {
                    onStateChanged(state.copy(context = it))
                }
            )
        }

        Permission(
            enabled = state.enabled && state.registered,
            permission = state.permission,
            onPermissionChanged = {
                onStateChanged(state.copy(permission = it))
            },
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}
