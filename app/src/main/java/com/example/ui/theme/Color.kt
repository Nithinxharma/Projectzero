package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Solo Leveling Dark Futuristic Palette
val BackgroundVoid = Color(0xFF08090E)
val BackgroundSurface = Color(0xFF0F1322)
val CardGlassBg = Color(0xCC13182B)
val CardGlassBgLight = Color(0xE6182038)
val GlassBorder = Color(0x3300F0FF)
val GlassBorderPurple = Color(0x409D4EDD)

// Neon Accents
val NeonCyan = Color(0xFF00F0FF)
val NeonCyanGlow = Color(0x6600F0FF)
val MonarchPurple = Color(0xFF9D4EDD)
val MonarchPurpleDark = Color(0xFF5A189A)
val ShadowIndigo = Color(0xFF3F37C9)
val LevelUpGold = Color(0xFFFFD700)
val HealthGreen = Color(0xFF00E676)
val AlertCrimson = Color(0xFFFF2A6D)
val WarningAmber = Color(0xFFFFB703)

// Text Colors
val TextPrimary = Color(0xFFF1F5F9)
val TextSecondary = Color(0xFF94A3B8)
val TextMuted = Color(0xFF64748B)
val TextCyan = Color(0xFF67E8F9)
val TextPurple = Color(0xFFC084FC)

// Gradients
val HunterGradient = Brush.horizontalGradient(
    colors = listOf(NeonCyan, MonarchPurple)
)

val MonarchGradient = Brush.verticalGradient(
    colors = listOf(MonarchPurple, ShadowIndigo, BackgroundVoid)
)

val CardGlassGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xCC19223D),
        Color(0x990F1424)
    )
)

val GoldGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFFDF00), Color(0xFFFF8C00))
)

val BossRedGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFFF2A6D), Color(0xFF790022))
)
