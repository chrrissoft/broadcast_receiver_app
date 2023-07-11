package com.chrrissoft.broadcastreceiver.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.sp


val Typography @Composable get()  = Typography(
    bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontWeight = Medium),
    titleLarge = MaterialTheme.typography.titleLarge.copy(fontWeight = Medium),
    labelSmall = MaterialTheme.typography.labelSmall.copy(fontWeight = Medium),
)
