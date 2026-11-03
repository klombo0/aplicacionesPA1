package com.example.aplicacionespa1.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MazajColorScheme = lightColorScheme(
    primary = PrimaryBrown,
    secondary = SecondaryOrange,
    background = BackgroundWhite,
    surface = BackgroundWhite,
    onPrimary = BackgroundWhite,
    onSecondary = BackgroundWhite,
    onBackground = TextDark,
    onSurface = TextDark
)

@Composable
fun AplicacionesPA1Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MazajColorScheme,
        typography = Typography,
        content = content
    )
}