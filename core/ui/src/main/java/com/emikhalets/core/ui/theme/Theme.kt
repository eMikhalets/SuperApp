package com.emikhalets.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Color.text get() = AppColor.Grey900

private val DarkColorPalette = darkColors(
)

private val LightColorPalette = lightColors(
    primary = AppColor.Grey600,
    onPrimary = Color.White,
    secondary = AppColor.Grey400,
    onSecondary = AppColor.Grey900,
    surface = Color.White,
    onSurface = AppColor.Grey900,
    background = AppColor.LightBlue50,
    onBackground = AppColor.Grey900,
    error = AppColor.Red500,
    onError = Color.White
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}