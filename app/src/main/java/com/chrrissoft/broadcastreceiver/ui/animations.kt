package com.chrrissoft.broadcastreceiver.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.ui.unit.IntOffset

val enterAnimatedVisibilityPage = slideInVertically(
    animationSpec = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold,
        dampingRatio = Spring.DampingRatioHighBouncy
    )
) { it / 10 } + fadeIn()