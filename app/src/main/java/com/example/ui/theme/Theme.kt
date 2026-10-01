package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Forced Solo Leveling Futuristic Cyber Dark Theme
private val HunterDarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = Color(0xFF001A24),
    primaryContainer = Color(0xFF00384D),
    onPrimaryContainer = Color(0xFFA6EFFF),
    secondary = MonarchPurple,
    onSecondary = Color(0xFF1E0045),
    secondaryContainer = Color(0xFF3B007A),
    onSecondaryContainer = Color(0xFFE8D0FF),
    tertiary = LevelUpGold,
    onTertiary = Color(0xFF332000),
    tertiaryContainer = Color(0xFF664400),
    onTertiaryContainer = Color(0xFFFFEAA8),
    error = AlertCrimson,
    onError = Color(0xFF38000F),
    errorContainer = Color(0xFF700021),
    onErrorContainer = Color(0xFFFFD9DF),
    background = BackgroundVoid,
    onBackground = TextPrimary,
    surface = BackgroundSurface,
    onSurface = TextPrimary,
    surfaceVariant = CardGlassBg,
    onSurfaceVariant = TextSecondary,
    outline = GlassBorder,
    outlineVariant = GlassBorderPurple
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // Always use Hunter Dark Theme for Solo Leveling Aesthetic
    MaterialTheme(
        colorScheme = HunterDarkColorScheme,
        typography = Typography,
        content = content
    )
}
