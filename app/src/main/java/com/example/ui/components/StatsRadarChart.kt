package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AlertCrimson
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.LevelUpGold
import com.example.ui.theme.MonarchPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ShadowIndigo
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.Typography
import kotlin.math.cos
import kotlin.math.sin

data class HunterStats(
    val strength: Int,
    val discipline: Int,
    val intelligence: Int,
    val focus: Int,
    val charisma: Int,
    val health: Int
)

@Composable
fun HunterRadarChart(
    stats: HunterStats,
    maxStatValue: Float = 30f,
    modifier: Modifier = Modifier
) {
    val labels = listOf("STR", "DIS", "INT", "FOC", "CHA", "HLT")
    val rawValues = listOf(
        stats.strength.toFloat(),
        stats.discipline.toFloat(),
        stats.intelligence.toFloat(),
        stats.focus.toFloat(),
        stats.charisma.toFloat(),
        stats.health.toFloat()
    )

    val animatedValues = rawValues.map { value ->
        animateFloatAsState(
            targetValue = value.coerceAtMost(maxStatValue) / maxStatValue,
            animationSpec = tween(1000),
            label = "stat_anim"
        ).value
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(210.dp)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(180.dp)) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.width / 2.2f
                val count = 6
                val angleStep = (2 * Math.PI / count).toFloat()

                // Draw background concentric hexagons
                val gridLevels = listOf(0.25f, 0.5f, 0.75f, 1.0f)
                gridLevels.forEach { level ->
                    val gridPath = Path()
                    for (i in 0 until count) {
                        val angle = i * angleStep - (Math.PI / 2).toFloat()
                        val x = center.x + radius * level * cos(angle)
                        val y = center.y + radius * level * sin(angle)
                        if (i == 0) gridPath.moveTo(x, y) else gridPath.lineTo(x, y)
                    }
                    gridPath.close()
                    drawPath(
                        path = gridPath,
                        color = Color(0x3300F0FF),
                        style = Stroke(width = if (level == 1f) 1.5f else 0.8f)
                    )
                }

                // Draw spokes from center
                for (i in 0 until count) {
                    val angle = i * angleStep - (Math.PI / 2).toFloat()
                    val x = center.x + radius * cos(angle)
                    val y = center.y + radius * sin(angle)
                    drawLine(
                        color = Color(0x2200F0FF),
                        start = center,
                        end = Offset(x, y),
                        strokeWidth = 1f
                    )
                }

                // Draw player stat polygon
                val statPath = Path()
                animatedValues.forEachIndexed { i, ratio ->
                    val angle = i * angleStep - (Math.PI / 2).toFloat()
                    val dist = radius * ratio.coerceIn(0.15f, 1.0f)
                    val x = center.x + dist * cos(angle)
                    val y = center.y + dist * sin(angle)
                    if (i == 0) statPath.moveTo(x, y) else statPath.lineTo(x, y)
                }
                statPath.close()

                // Fill polygon with glowing purple-cyan gradient
                drawPath(
                    path = statPath,
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MonarchPurple.copy(alpha = 0.55f),
                            NeonCyan.copy(alpha = 0.35f),
                            Color.Transparent
                        ),
                        center = center,
                        radius = radius
                    ),
                    style = Fill
                )

                // Draw polygon border
                drawPath(
                    path = statPath,
                    color = NeonCyan,
                    style = Stroke(width = 2.5f)
                )

                // Draw vertex glowing dots
                animatedValues.forEachIndexed { i, ratio ->
                    val angle = i * angleStep - (Math.PI / 2).toFloat()
                    val dist = radius * ratio.coerceIn(0.15f, 1.0f)
                    val x = center.x + dist * cos(angle)
                    val y = center.y + dist * sin(angle)
                    drawCircle(
                        color = LevelUpGold,
                        radius = 4f,
                        center = Offset(x, y)
                    )
                }
            }
        }

        // Stats summary chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatPill(name = "STR", value = stats.strength, color = AlertCrimson)
            StatPill(name = "DIS", value = stats.discipline, color = MonarchPurple)
            StatPill(name = "INT", value = stats.intelligence, color = NeonCyan)
            StatPill(name = "FOC", value = stats.focus, color = LevelUpGold)
            StatPill(name = "CHA", value = stats.charisma, color = Color(0xFFFF80BF))
            StatPill(name = "HLT", value = stats.health, color = HealthGreen)
        }
    }
}

@Composable
private fun StatPill(
    name: String,
    value: Int,
    color: Color
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = name,
                style = Typography.labelSmall.copy(fontSize = 9.sp),
                color = color
            )
            Text(
                text = "$value",
                style = Typography.labelLarge.copy(fontSize = 12.sp),
                color = TextPrimary
            )
        }
    }
}
