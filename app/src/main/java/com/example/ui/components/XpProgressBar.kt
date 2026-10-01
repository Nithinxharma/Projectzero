package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AlertCrimson
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.LevelUpGold
import com.example.ui.theme.MonarchPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.Typography

@Composable
fun HunterProgressBar(
    current: Int,
    max: Int,
    label: String,
    gradient: Brush,
    barHeight: Dp = 10.dp,
    modifier: Modifier = Modifier,
    trailingText: String? = null
) {
    val progress = (current.toFloat() / max.coerceAtLeast(1).toFloat()).coerceIn(0f, 1f)
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 600),
        label = "progress_anim"
    ).value

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label.uppercase(),
                style = Typography.labelMedium.copy(fontSize = 11.sp),
                color = TextSecondary
            )
            Text(
                text = trailingText ?: "$current / $max",
                style = Typography.labelMedium.copy(fontSize = 11.sp),
                color = TextPrimary
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .clip(RoundedCornerShape(barHeight / 2))
                .background(Color(0xFF0F1424))
                .border(1.dp, Color(0x3300F0FF), RoundedCornerShape(barHeight / 2))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(barHeight / 2))
                    .background(gradient)
            )
        }
    }
}

@Composable
fun HunterXpBar(
    currentXp: Int,
    maxXp: Int,
    level: Int,
    modifier: Modifier = Modifier
) {
    HunterProgressBar(
        current = currentXp,
        max = maxXp,
        label = "LEVEL $level  •  XP PROGRESS",
        gradient = Brush.horizontalGradient(
            listOf(NeonCyan, MonarchPurple, LevelUpGold)
        ),
        barHeight = 12.dp,
        modifier = modifier,
        trailingText = "$currentXp / $maxXp XP (${((currentXp.toFloat() / maxXp.coerceAtLeast(1)) * 100).toInt()}%)"
    )
}

@Composable
fun HunterHpBar(
    currentHp: Int,
    maxHp: Int,
    modifier: Modifier = Modifier
) {
    HunterProgressBar(
        current = currentHp,
        max = maxHp,
        label = "HP (HEALTH POINTS)",
        gradient = Brush.horizontalGradient(
            listOf(HealthGreen, Color(0xFF00B0FF))
        ),
        barHeight = 8.dp,
        modifier = modifier
    )
}

@Composable
fun BossHpBar(
    currentHp: Int,
    maxHp: Int,
    bossName: String,
    modifier: Modifier = Modifier
) {
    HunterProgressBar(
        current = currentHp,
        max = maxHp,
        label = "BOSS HP • $bossName",
        gradient = Brush.horizontalGradient(
            listOf(AlertCrimson, Color(0xFFFF5252), Color(0xFFFF8A80))
        ),
        barHeight = 14.dp,
        modifier = modifier,
        trailingText = "$currentHp / $maxHp HP"
    )
}
