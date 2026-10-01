package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Real-Person Premium Dark Performance Palette
val BackgroundVoid = Color(0xFF0C0E14)
val BackgroundSurface = Color(0xFF141824)
val CardGlassBg = Color(0xEE1A2030)
val CardGlassBgLight = Color(0xF2222B40)
val GlassBorder = Color(0x3338BDF8)
val GlassBorderPurple = Color(0x33818CF8)

// Realistic Modern Performance Accents
val NeonCyan = Color(0xFF10B981) // Crisp Emerald Green for achievement & health
val NeonCyanGlow = Color(0x3310B981)
val MonarchPurple = Color(0xFF6366F1) // Indigo for deep cognitive focus
val MonarchPurpleDark = Color(0xFF4338CA)
val ShadowIndigo = Color(0xFF2563EB) // Electric Sapphire Blue for habits
val LevelUpGold = Color(0xFFF59E0B) // Amber for consistency streaks & milestones
val HealthGreen = Color(0xFF10B981) // Vitality Emerald
val AlertCrimson = Color(0xFFEF4444) // Coral red for urge / emergency trigger
val WarningAmber = Color(0xFFF59E0B)

// Slate & Pure Text Colors
val TextPrimary = Color(0xFFF8FAFC)
val TextSecondary = Color(0xFF94A3B8)
val TextMuted = Color(0xFF64748B)
val TextCyan = Color(0xFF34D399)
val TextPurple = Color(0xFFA5B4FC)

// Elegant Real-World Gradients
val HunterGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF10B981), Color(0xFF3B82F6))
)

val MonarchGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF1E293B), Color(0xFF0F172A), BackgroundVoid)
)

val CardGlassGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF1E2638),
        Color(0xFF131926)
    )
)

val GoldGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFF59E0B), Color(0xFFD97706))
)

val BossRedGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFEF4444), Color(0xFF991B1B))
)
