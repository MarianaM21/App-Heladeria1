package com.example.appheladeria.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = PrimaryPink,
    secondary = SecondaryPink,
    background = BackgroundSoft,
    surface = WhiteCard,
    surfaceVariant = CardSoft,
    onPrimary = Color.White,
    onSecondary = TextDark,
    onBackground = TextDark,
    onSurface = TextDark,
    onSurfaceVariant = TextMuted
)

@Composable
fun AppHeladeriaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}