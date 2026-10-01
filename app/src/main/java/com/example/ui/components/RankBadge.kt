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
import androidx.compose.material.icons.filled.Verified
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
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.LevelUpGold
import com.example.ui.theme.ShadowIndigo
import com.example.ui.theme.Typography

data class PerformanceTierTheme(
    val tierLabel: String,
    val title: String,
    val color: Color,
    val bgColor: Color
)

fun getPerformanceTierTheme(rank: String): PerformanceTierTheme {
    return when (rank.uppercase()) {
        "MONARCH", "MASTERY" -> PerformanceTierTheme(
            tierLabel = "TIER IV",
            title = "MASTERY",
            color = LevelUpGold,
            bgColor = Color(0x33F59E0B)
        )
        "NATIONAL", "ADVANCED" -> PerformanceTierTheme(
            tierLabel = "TIER III",
            title = "DISCIPLINED",
            color = Color(0xFF38BDF8),
            bgColor = Color(0x3338BDF8)
        )
        "S", "A", "CONSISTENT" -> PerformanceTierTheme(
            tierLabel = "TIER II",
            title = "CONSISTENT",
            color = HealthGreen,
            bgColor = Color(0x3310B981)
        )
        else -> PerformanceTierTheme(
            tierLabel = "TIER I",
            title = "PRACTITIONER",
            color = Color(0xFF94A3B8),
            bgColor = Color(0x3364748B)
        )
    }
}

@Composable
fun RankBadge(
    rank: String,
    modifier: Modifier = Modifier,
    showTitle: Boolean = false
) {
    val theme = getPerformanceTierTheme(rank)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(theme.bgColor)
            .border(1.dp, theme.color.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = null,
                tint = theme.color,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (showTitle) "${theme.tierLabel} • ${theme.title}" else theme.title,
                style = Typography.labelSmall.copy(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                ),
                color = theme.color
            )
        }
    }
}
