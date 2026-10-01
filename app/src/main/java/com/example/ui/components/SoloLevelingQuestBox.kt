package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.QuestEntity
import com.example.ui.theme.AlertCrimson
import com.example.ui.theme.BackgroundVoid
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.LevelUpGold
import com.example.ui.theme.MonarchPurple
import com.example.ui.theme.NeonCyan
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
    val infiniteTransition = rememberInfiniteTransition(label = "hologram_pulse")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    val allDailyCompleted = quests.isNotEmpty() && quests.all { it.isCompleted }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(12.dp), spotColor = NeonCyan.copy(alpha = 0.35f))
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xE6080E1C))
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        NeonCyan.copy(alpha = glowAlpha),
                        MonarchPurple.copy(alpha = 0.7f),
                        NeonCyan.copy(alpha = glowAlpha * 0.8f)
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .testTag("solo_leveling_quest_window")
    ) {
        // Holographic System Window Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF003853), Color(0xFF1E084D), Color(0xFF003853))
                    )
                )
                .border(
                    width = 1.dp,
                    color = NeonCyan.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(if (allDailyCompleted) HealthGreen else NeonCyan)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "QUEST INFO : DAILY QUEST",
                        style = Typography.labelLarge.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.2.sp
                        ),
                        color = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (allDailyCompleted) HealthGreen.copy(alpha = 0.25f) else AlertCrimson.copy(alpha = 0.25f))
                        .border(1.dp, if (allDailyCompleted) HealthGreen else AlertCrimson, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (allDailyCompleted) "CLEARED" else "INCOMPLETE",
                        style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = if (allDailyCompleted) HealthGreen else AlertCrimson
                    )
                }
            }
        }

        // Sub-title Banner
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Text(
                text = "【 DAILY QUEST : PREPARATION TO BECOME STRONG 】",
                style = Typography.titleMedium.copy(
                    fontWeight = FontWeight.Black,
                    fontSize = 15.sp,
                    letterSpacing = 0.5.sp
                ),
                color = TextCyan
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Goals must be executed daily from Zero to Target Limit. Increment reps as you perform each set.",
                style = Typography.bodyMedium.copy(fontSize = 12.sp),
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Exercise Goals 0 to N Matrix List
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

            Spacer(modifier = Modifier.height(16.dp))

            // Warning Penalty Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0x33FF2A6D))
                    .border(1.dp, AlertCrimson.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = AlertCrimson,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "SYSTEM WARNING : PENALTY QUEST",
                            style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = AlertCrimson
                        )
                        Text(
                            text = "Failure to complete daily goals before midnight will transfer you into the Penalty Survival Zone for 4 Hours.",
                            style = Typography.bodyMedium.copy(fontSize = 11.sp),
                            color = Color(0xFFFFD0D8)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // System Rewards Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0x269D4EDD))
                    .border(1.dp, MonarchPurple.copy(alpha = 0.7f), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "★ QUEST CLEAR REWARDS",
                            style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = LevelUpGold
                        )
                        Text(
                            text = "+300 XP • +3 STAT PTS",
                            style = Typography.labelSmall,
                            color = NeonCyan
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "1. Status Recovery (Restores 100% HP & MP)\n2. +3 Free Attribute Distribution Points\n3. Blessed Random Loot Box",
                        style = Typography.bodyMedium.copy(fontSize = 11.sp, lineHeight = 16.sp),
                        color = Color(0xFFE2E8F0)
                    )
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
            .background(if (isComplete) Color(0x3300E676) else Color(0x330E1428))
            .border(
                width = 1.dp,
                color = if (isComplete) HealthGreen.copy(alpha = 0.6f) else Color(0x3300F0FF),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { expandedControls = !expandedControls }
            .padding(10.dp)
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
                        .background(if (isComplete) HealthGreen.copy(alpha = 0.2f) else Color(0x2200F0FF))
                        .border(1.dp, if (isComplete) HealthGreen else NeonCyan, CircleShape),
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
                            text = "-",
                            style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = NeonCyan
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
                        text = if (isComplete) "COMPLETED" else "INCOMPLETE",
                        style = Typography.labelSmall.copy(fontSize = 10.sp),
                        color = if (isComplete) HealthGreen else Color(0xFFEF4444)
                    )
                }
            }

            // Target Limit indicator e.g. [45/100]
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF090D1C))
                        .border(1.dp, if (isComplete) HealthGreen else NeonCyan, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "[$current / $target ${quest.unit}]",
                        style = Typography.labelLarge.copy(
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        ),
                        color = if (isComplete) HealthGreen else NeonCyan
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

        Spacer(modifier = Modifier.height(6.dp))

        // Progress line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Color(0xFF090D1C))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        if (isComplete) Brush.horizontalGradient(listOf(HealthGreen, Color(0xFF00B0FF)))
                        else Brush.horizontalGradient(listOf(NeonCyan, MonarchPurple))
                    )
            )
        }

        // Expanded Increment Rep Buttons
        AnimatedVisibility(visible = expandedControls || !isComplete) {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val stepOptions = when (quest.unit) {
                        "km" -> listOf(1, 2, 5)
                        "ml" -> listOf(250, 500, 1000)
                        "mins" -> listOf(15, 30, 45)
                        else -> listOf(5, 10, 25, 50)
                    }

                    stepOptions.forEach { step ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0x3300F0FF))
                                .border(1.dp, NeonCyan.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                .clickable { onIncrement(step) }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+$step",
                                style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = NeonCyan
                            )
                        }
                    }

                    if (quest.requiresCameraProof) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(MonarchPurple.copy(alpha = 0.4f))
                                .border(1.dp, MonarchPurple, RoundedCornerShape(6.dp))
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
                                Text("PROOF", style = Typography.labelSmall.copy(fontSize = 9.sp), color = LevelUpGold)
                            }
                        }
                    }
                }
            }
        }
    }
}
