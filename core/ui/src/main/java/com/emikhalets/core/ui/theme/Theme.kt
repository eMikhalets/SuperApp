package com.emikhalets.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Colors.stroke get() = AppColor.Grey200

private val DarkColorPalette = darkColors(
)

private val LightColorPalette = lightColors(
    primary = AppColor.Grey600,
    onPrimary = Color.White,
    secondary = AppColor.Grey400,
    onSecondary = AppColor.Grey800,
    surface = Color.White,
    onSurface = AppColor.Grey900,
    background = AppColor.Grey100,
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

    Surface(color = MaterialTheme.colors.background) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}