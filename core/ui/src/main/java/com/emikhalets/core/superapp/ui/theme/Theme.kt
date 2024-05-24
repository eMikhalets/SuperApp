package com.emikhalets.core.superapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color.BlueA700,
    onPrimary = Color.White,
    primaryContainer = Color.BlueA700,
    onPrimaryContainer = Color.White,
    secondary = Color.LightBlue100,
    onSecondary = Color.White,
    tertiary = Color.LightBlue400,
    onTertiary = Color.White,
    error = Color.Red800,
    onError = Color.White,
    background = Color.Grey100,
    onBackground = Color.Grey900,
    surface = Color.Grey100,
    onSurface = Color.Grey900,
    onSurfaceVariant = Color.Grey700,
    outline = Color.Grey700,
)

// TODO: set dark colors
private val DarkColors = darkColorScheme(
)

@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}