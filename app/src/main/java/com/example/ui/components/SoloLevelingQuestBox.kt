package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.QuestEntity
import com.example.ui.theme.AlertCrimson
import com.example.ui.theme.BackgroundSurface
import com.example.ui.theme.BackgroundVoid
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.LevelUpGold
import com.example.ui.theme.MonarchPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ShadowIndigo
import com.example.ui.theme.TextCyan
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.Typography

@Composable
fun SoloLevelingSystemQuestWindow(
    quests: List<QuestEntity>,
    onIncrementReps: (QuestEntity, Int) -> Unit,
    onEditTargetLimit: (QuestEntity) -> Unit,
    onVerifyProof: (QuestEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val completedCount = quests.count { it.isCompleted }
    val totalCount = quests.size
    val allDailyCompleted = totalCount > 0 && completedCount == totalCount

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF141926))
            .border(
                width = 1.dp,
                color = if (allDailyCompleted) HealthGreen.copy(alpha = 0.6f) else ShadowIndigo.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )
            .testTag("solo_leveling_quest_window")
    ) {
        // Real-Life Protocol Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A2234))
                .border(
                    width = 1.dp,
                    color = ShadowIndigo.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                        contentDescription = null,
                        tint = if (allDailyCompleted) HealthGreen else NeonCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "TODAY'S DISCIPLINE PROTOCOL",
                        style = Typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        ),
                        color = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (allDailyCompleted) HealthGreen.copy(alpha = 0.2f) else ShadowIndigo.copy(alpha = 0.25f))
                        .border(1.dp, if (allDailyCompleted) HealthGreen else ShadowIndigo, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "$completedCount / $totalCount DONE",
                        style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = if (allDailyCompleted) HealthGreen else Color(0xFF93C5FD)
                    )
                }
            }
        }

        // Sub-title Banner
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Physical Fitness & Cognitive Habits",
                style = Typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                ),
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Execute your daily target volume. Tap quick increment buttons (+5, +10, etc.) as you complete each set.",
                style = Typography.bodyMedium.copy(fontSize = 12.sp),
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Exercise Goals Matrix List
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                quests.forEach { quest ->
                    CountableExerciseRow(
                        quest = quest,
                        onIncrement = { delta -> onIncrementReps(quest, delta) },
                        onEditTarget = { onEditTargetLimit(quest) },
                        onProof = { onVerifyProof(quest) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Clean Living & Accountability Callout
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF161E2E))
                    .border(1.dp, ShadowIndigo.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.SelfImprovement,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "ATOMIC HABIT PRINCIPLE",
                            style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = NeonCyan
                        )
                        Text(
                            text = "Consistency beats intensity. Small daily actions compound into life-changing physical and cognitive results.",
                            style = Typography.bodyMedium.copy(fontSize = 11.sp),
                            color = TextSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CountableExerciseRow(
    quest: QuestEntity,
    onIncrement: (Int) -> Unit,
    onEditTarget: () -> Unit,
    onProof: () -> Unit
) {
    val isComplete = quest.isCompleted
    val current = quest.currentProgress
    val target = quest.targetLimit
    val progress = (current.toFloat() / target.coerceAtLeast(1).toFloat()).coerceIn(0f, 1f)

    var expandedControls by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isComplete) Color(0x1F10B981) else Color(0xFF1B2232))
            .border(
                width = 1.dp,
                color = if (isComplete) HealthGreen.copy(alpha = 0.5f) else Color(0x3338BDF8),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { expandedControls = !expandedControls }
            .padding(12.dp)
            .testTag("exercise_row_${quest.id}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(if (isComplete) HealthGreen.copy(alpha = 0.2f) else Color(0x2238BDF8))
                        .border(1.dp, if (isComplete) HealthGreen else ShadowIndigo, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (isComplete) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = HealthGreen,
                            modifier = Modifier.size(14.dp)
                        )
                    } else {
                        Text(
                            text = "•",
                            style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF38BDF8)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = quest.title.replace("\\[.*?\\]".toRegex(), "").trim(),
                        style = Typography.titleMedium.copy(fontSize = 13.sp, fontWeight = FontWeight.SemiBold),
                        color = if (isComplete) HealthGreen else TextPrimary
                    )
                    Text(
                        text = if (isComplete) "Target Reached" else "${target - current} ${quest.unit} remaining",
                        style = Typography.labelSmall.copy(fontSize = 11.sp),
                        color = if (isComplete) HealthGreen else TextSecondary
                    )
                }
            }

            // Target Limit indicator e.g. [45/100]
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF0F1420))
                        .border(1.dp, if (isComplete) HealthGreen.copy(alpha = 0.4f) else ShadowIndigo.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "$current / $target ${quest.unit}",
                        style = Typography.labelLarge.copy(
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        ),
                        color = if (isComplete) HealthGreen else Color(0xFF60A5FA)
                    )
                }

                IconButton(
                    onClick = onEditTarget,
                    modifier = Modifier.size(28.dp).padding(start = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit target limit",
                        tint = TextSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Progress line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Color(0xFF0F1420))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        if (isComplete) Brush.horizontalGradient(listOf(HealthGreen, Color(0xFF059669)))
                        else Brush.horizontalGradient(listOf(Color(0xFF38BDF8), ShadowIndigo))
                    )
            )
        }

        // Expanded Increment Rep Buttons
        AnimatedVisibility(visible = expandedControls || !isComplete) {
            Column(modifier = Modifier.padding(top = 10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val stepOptions = when (quest.unit) {
                        "km" -> listOf(1, 2, 5)
                        "ml" -> listOf(250, 500, 1000)
                        "mins" -> listOf(15, 30, 45)
                        "pages" -> listOf(5, 10, 20)
                        else -> listOf(5, 10, 25, 50)
                    }

                    stepOptions.forEach { step ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF1E283D))
                                .border(1.dp, ShadowIndigo.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                            .clickable { onIncrement(step) }
                            .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+$step",
                                style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF93C5FD)
                            )
                        }
                    }

                    if (quest.requiresCameraProof) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(LevelUpGold.copy(alpha = 0.15f))
                                .border(1.dp, LevelUpGold.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                .clickable { onProof() }
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = null,
                                    tint = LevelUpGold,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text("PHOTO", style = Typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold), color = LevelUpGold)
                            }
                        }
                    }
                }
            }
        }
    }
}
