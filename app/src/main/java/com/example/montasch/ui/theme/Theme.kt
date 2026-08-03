package com.example.montasch.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FropColorScheme = lightColorScheme(
    primary = FropOrange,
    secondary = FropNavy,
    tertiary = FropGreen,
    background = FropCream,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = FropInk,
    onSurface = FropInk
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
