package com.chrrissoft.broadcastreceiver.ui.theme

import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val cardColors
    @Composable get() = CardDefaults.cardColors(
        containerColor = colorScheme.primaryContainer,
    )

@OptIn(ExperimentalMaterial3Api::class)
val topAppBarColors
    @Composable get() = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = colorScheme.primaryContainer,
        titleContentColor = colorScheme.primary,
        actionIconContentColor = colorScheme.primary,
        navigationIconContentColor = colorScheme.primary
    )

val navItemBarColors
    @Composable get() = NavigationBarItemDefaults.colors(
        selectedIconColor = colorScheme.onPrimary,
        selectedTextColor = colorScheme.primary,
        unselectedIconColor = colorScheme.primary.copy(.4f),
        unselectedTextColor = colorScheme.secondary.copy(.4f),
        indicatorColor = colorScheme.primary,
    )

val checkboxColors
    @Composable get() = CheckboxDefaults.colors(
        checkedColor = colorScheme.primary,
        uncheckedColor = colorScheme.secondary,
        checkmarkColor = colorScheme.onPrimary,
        disabledCheckedColor = colorScheme.primary.copy(.5f),
        disabledUncheckedColor = Color.Transparent,
        disabledIndeterminateColor = Color.Red,
    )

@OptIn(ExperimentalMaterial3Api::class)
val inputChipColors
    @Composable get() = InputChipDefaults.inputChipColors(
        containerColor = colorScheme.primaryContainer,
        labelColor = colorScheme.primary,
        leadingIconColor = colorScheme.primary,
        trailingIconColor = colorScheme.primary,
        disabledContainerColor = colorScheme.primaryContainer.copy(.5f),
        disabledLabelColor = colorScheme.primary.copy(.5f),
        disabledLeadingIconColor = colorScheme.secondary.copy(.5f),
        disabledTrailingIconColor = colorScheme.secondary.copy(.5f),
        selectedContainerColor = colorScheme.primary,
        disabledSelectedContainerColor = colorScheme.primary.copy(.5f),
        selectedLabelColor = colorScheme.onPrimary,
        selectedLeadingIconColor = colorScheme.onPrimary,
        selectedTrailingIconColor = colorScheme.onPrimary,
    )

@OptIn(ExperimentalMaterial3Api::class)
val inputChipBorderColor
    @Composable get() = InputChipDefaults.inputChipBorder(
        borderColor = colorScheme.primary
    )

val navigationDrawerItemColors
    @Composable get() = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = colorScheme.primary,
        unselectedContainerColor = colorScheme.onPrimary,
        selectedIconColor = colorScheme.onPrimary,
        unselectedIconColor = colorScheme.secondary.copy(.5f),
        selectedTextColor = colorScheme.onPrimary,
        unselectedTextColor = colorScheme.secondary.copy(.5f),
        selectedBadgeColor = colorScheme.onPrimary,
        unselectedBadgeColor = colorScheme.secondary.copy(.5f),
    )