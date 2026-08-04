package com.example.montasch.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily

// Do not bundle binary font assets in the APK. Android's platform sans-serif
// family is available on every supported device and needs no font resource.
private val AppFontFamily = FontFamily.SansSerif

private val MaterialTypography = Typography()

/** Material 3 typography with one consistent, resource-free font family. */
val Typography = Typography(
    displayLarge = MaterialTypography.displayLarge.copy(fontFamily = AppFontFamily),
    displayMedium = MaterialTypography.displayMedium.copy(fontFamily = AppFontFamily),
    displaySmall = MaterialTypography.displaySmall.copy(fontFamily = AppFontFamily),
    headlineLarge = MaterialTypography.headlineLarge.copy(fontFamily = AppFontFamily),
    headlineMedium = MaterialTypography.headlineMedium.copy(fontFamily = AppFontFamily),
    headlineSmall = MaterialTypography.headlineSmall.copy(fontFamily = AppFontFamily),
    titleLarge = MaterialTypography.titleLarge.copy(fontFamily = AppFontFamily),
    titleMedium = MaterialTypography.titleMedium.copy(fontFamily = AppFontFamily),
    titleSmall = MaterialTypography.titleSmall.copy(fontFamily = AppFontFamily),
    bodyLarge = MaterialTypography.bodyLarge.copy(fontFamily = AppFontFamily),
    bodyMedium = MaterialTypography.bodyMedium.copy(fontFamily = AppFontFamily),
    bodySmall = MaterialTypography.bodySmall.copy(fontFamily = AppFontFamily),
    labelLarge = MaterialTypography.labelLarge.copy(fontFamily = AppFontFamily),
    labelMedium = MaterialTypography.labelMedium.copy(fontFamily = AppFontFamily),
    labelSmall = MaterialTypography.labelSmall.copy(fontFamily = AppFontFamily)
)
