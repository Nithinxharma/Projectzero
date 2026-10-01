package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AlertCrimson
import com.example.ui.theme.LevelUpGold
import com.example.ui.theme.MonarchPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.Typography

data class RankTheme(
    val rankLetter: String,
    val rankTitle: String,
    val primaryColor: Color,
    val secondaryColor: Color,
    val glowColor: Color
)

fun getRankTheme(rank: String): RankTheme {
    return when (rank.uppercase()) {
        "MONARCH" -> RankTheme(
            rankLetter = "👑",
            rankTitle = "SHADOW MONARCH",
            primaryColor = MonarchPurple,
            secondaryColor = Color(0xFF5A189A),
            glowColor = Color(0xFFE0AAFF)
        )
        "NATIONAL" -> RankTheme(
            rankLetter = "NAT",
            rankTitle = "NATIONAL HUNTER",
            primaryColor = LevelUpGold,
            secondaryColor = Color(0xFFFF9E00),
            glowColor = Color(0xFFFFE066)
        )
        "S" -> RankTheme(
            rankLetter = "S",
            rankTitle = "S-RANK HUNTER",
            primaryColor = AlertCrimson,
            secondaryColor = Color(0xFFC1121F),
            glowColor = Color(0xFFFF758F)
        )
        "A" -> RankTheme(
            rankLetter = "A",
            rankTitle = "A-RANK HUNTER",
            primaryColor = Color(0xFFFF9F1C),
            secondaryColor = Color(0xFFD97706),
            glowColor = Color(0xFFFFD166)
        )
        "B" -> RankTheme(
            rankLetter = "B",
            rankTitle = "B-RANK HUNTER",
            primaryColor = NeonCyan,
            secondaryColor = Color(0xFF0096C7),
            glowColor = Color(0xFF90E0EF)
        )
        "C" -> RankTheme(
            rankLetter = "C",
            rankTitle = "C-RANK HUNTER",
            primaryColor = Color(0xFF06D6A0),
            secondaryColor = Color(0xFF059669),
            glowColor = Color(0xFFA7F3D0)
        )
        "D" -> RankTheme(
            rankLetter = "D",
            rankTitle = "D-RANK HUNTER",
            primaryColor = Color(0xFF38BDF8),
            secondaryColor = Color(0xFF0284C7),
            glowColor = Color(0xFFBAE6FD)
        )
        else -> RankTheme(
            rankLetter = "E",
            rankTitle = "E-RANK AWAKENED",
            primaryColor = Color(0xFF94A3B8),
            secondaryColor = Color(0xFF475569),
            glowColor = Color(0xFFCBD5E1)
        )
    }
}

@Composable
fun RankBadge(
    rank: String,
    modifier: Modifier = Modifier,
    showTitle: Boolean = false
) {
    val theme = getRankTheme(rank)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        theme.primaryColor.copy(alpha = 0.25f),
                        theme.secondaryColor.copy(alpha = 0.15f)
                    )
                )
            )
            .border(
                width = 1.5.dp,
                brush = Brush.linearGradient(
                    listOf(theme.glowColor, theme.primaryColor)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                tint = theme.glowColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (showTitle) "${theme.rankLetter} • ${theme.rankTitle}" else "RANK ${theme.rankLetter}",
                style = Typography.labelLarge.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black
                ),
                color = theme.glowColor
            )
        }
    }
}
