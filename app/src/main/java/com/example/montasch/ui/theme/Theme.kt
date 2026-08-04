package com.example.montasch.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FropColorScheme = lightColorScheme(
    primary = FropPrimary,
    secondary = FropBorder,
    tertiary = FropWarning,
    background = FropWhite,
    surface = FropWhite,
    onPrimary = FropWhite,
    onSecondary = FropInk,
    onTertiary = FropInk,
    onBackground = FropInk,
    onSurface = FropInk,
    outline = FropBorder
)

@Composable
fun MontaschTheme(
    @Suppress("UNUSED_PARAMETER") darkTheme: Boolean = false,
    @Suppress("UNUSED_PARAMETER") dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // FROP colors stay consistent on every Android version and system theme.
    MaterialTheme(
        colorScheme = FropColorScheme,
        typography = Typography,
        content = content
    )
}
