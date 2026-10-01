package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.LevelUpGold
import com.example.ui.theme.MonarchPurple
import com.example.ui.theme.NeonCyan
import kotlin.random.Random

private data class Particle(
    val xRatio: Float,
    val yRatio: Float,
    val radius: Float,
    val speed: Float,
    val color: Color,
    val alphaOffset: Float
)

@Composable
fun SystemAmbientParticles(
    modifier: Modifier = Modifier,
    particleCount: Int = 30
) {
    val infiniteTransition = rememberInfiniteTransition(label = "particles")
    val progress = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particle_motion"
    )

    val particles = remember {
        val colors = listOf(
            NeonCyan.copy(alpha = 0.6f),
            MonarchPurple.copy(alpha = 0.5f),
            LevelUpGold.copy(alpha = 0.4f),
            Color(0xFF67E8F9).copy(alpha = 0.5f)
        )
        List(particleCount) {
            Particle(
                xRatio = Random.nextFloat(),
                yRatio = Random.nextFloat(),
                radius = Random.nextFloat() * 3f + 1.5f,
                speed = Random.nextFloat() * 0.4f + 0.6f,
                color = colors.random(),
                alphaOffset = Random.nextFloat()
            )
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        particles.forEach { particle ->
            val animatedY = (particle.yRatio - (progress.value * particle.speed)) % 1f
            val actualY = if (animatedY < 0) animatedY + 1f else animatedY
            val currentX = particle.xRatio * width
            val currentY = actualY * height

            val pulseAlpha = (Math.sin((progress.value + particle.alphaOffset) * 2 * Math.PI).toFloat() + 1f) / 2f
            val drawColor = particle.color.copy(alpha = (particle.color.alpha * (0.3f + 0.7f * pulseAlpha)))

            drawCircle(
                color = drawColor,
                radius = particle.radius,
                center = Offset(currentX, currentY)
            )
        }
    }
}
