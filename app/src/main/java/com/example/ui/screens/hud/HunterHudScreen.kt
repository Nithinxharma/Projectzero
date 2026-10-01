package com.example.ui.screens.hud

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.QuestEntity
import com.example.ui.components.AdminCommandCenterModal
import com.example.ui.components.CameraProofScanner
import com.example.ui.components.EditTargetLimitDialog
import com.example.ui.components.GlassCard
import com.example.ui.components.HunterProgressBar
import com.example.ui.components.HunterRadarChart
import com.example.ui.components.HunterStats
import com.example.ui.components.RankBadge
import com.example.ui.components.SoloLevelingSystemQuestWindow
import com.example.ui.components.SystemSectionHeader
import com.example.ui.theme.AlertCrimson
import com.example.ui.theme.BackgroundSurface
import com.example.ui.theme.BackgroundVoid
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.LevelUpGold
import com.example.ui.theme.MonarchPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ShadowIndigo
import com.example.ui.theme.TextCyan
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.Typography
import com.example.ui.viewmodel.HunterViewModel

@Composable
fun HunterHudScreen(
    viewModel: HunterViewModel,
    onNavigateToQuests: () -> Unit,
    onNavigateToCoach: () -> Unit,
    onNavigateToProof: () -> Unit,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.profile.collectAsState()
    val allQuests by viewModel.allQuests.collectAsState()
    val bossRaids by viewModel.bossRaids.collectAsState()
    val isVerifying by viewModel.isVerifyingProof.collectAsState()

    var activeProofQuest by remember { mutableStateOf<QuestEntity?>(null) }
    var editingLimitQuest by remember { mutableStateOf<QuestEntity?>(null) }
    var showUrgeDeflectorModal by remember { mutableStateOf(false) }
    var showProfileSettingsModal by remember { mutableStateOf(false) }

    val dailyCountableQuests = allQuests.filter { it.category == "DAILY" }
    val completedCount = dailyCountableQuests.count { it.isCompleted }
    val totalCount = dailyCountableQuests.size

    Box(modifier = modifier.fillMaxSize().background(BackgroundVoid)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. TOP PROFILE & ACCOUNT CARD
            item {
                profile?.let { p ->
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("hunter_hud_card"),
                        borderColor = ShadowIndigo.copy(alpha = 0.5f),
                        borderWidth = 1.dp,
                        backgroundColor = Color(0xFF141926)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    // User Avatar Monogram
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(CircleShape)
                                            .background(
                                                Brush.radialGradient(
                                                    listOf(Color(0xFF38BDF8), ShadowIndigo)
                                                )
                                            )
                                            .border(1.5.dp, Color(0xFF38BDF8), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = p.hunterName.take(1).uppercase(),
                                            style = Typography.titleLarge.copy(
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = p.hunterName,
                                                style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                                color = TextPrimary
                                            )
                                        }
                                        Text(
                                            text = p.title,
                                            style = Typography.labelSmall,
                                            color = Color(0xFF94A3B8)
                                        )
                                        Text(
                                            text = p.userEmail,
                                            style = Typography.labelSmall.copy(fontSize = 10.sp),
                                            color = TextMuted
                                        )
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RankBadge(rank = p.rank, showTitle = false)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    IconButton(
                                        onClick = { showProfileSettingsModal = true },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit Profile",
                                            tint = Color(0xFF94A3B8),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Overall Consistency Bar
                            HunterProgressBar(
                                current = p.currentXp,
                                max = p.maxXp,
                                label = "DAILY ADHERENCE SCORE",
                                gradient = Brush.horizontalGradient(
                                    listOf(Color(0xFF10B981), Color(0xFF3B82F6))
                                ),
                                barHeight = 8.dp,
                                trailingText = "${p.consistencyScore}% Adherence"
                            )
                        }
                    }
                }
            }

            // 2. KEY PERFORMANCE TILES (Clean Streak & Today's Volume)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Clean Streak Tile
                    GlassCard(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showUrgeDeflectorModal = true },
                        borderColor = HealthGreen.copy(alpha = 0.4f),
                        backgroundColor = Color(0xFF131F25)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "CLEAN STREAK",
                                    style = Typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold),
                                    color = HealthGreen
                                )
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = null,
                                    tint = HealthGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "${profile?.tobaccoFreeDays ?: 14} DAYS",
                                style = Typography.headlineSmall.copy(fontWeight = FontWeight.Black),
                                color = TextPrimary
                            )
                            Text(
                                text = "Smoke & Tobacco Free",
                                style = Typography.labelSmall.copy(fontSize = 10.sp),
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(AlertCrimson.copy(alpha = 0.2f))
                                    .border(1.dp, AlertCrimson.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                    .padding(vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "🚨 CRAVING PAUSE",
                                    style = Typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold),
                                    color = AlertCrimson
                                )
                            }
                        }
                    }

                    // Habits Completed Tile
                    GlassCard(
                        modifier = Modifier.weight(1f),
                        borderColor = ShadowIndigo.copy(alpha = 0.4f),
                        backgroundColor = Color(0xFF141926)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "TODAY'S TARGETS",
                                    style = Typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold),
                                    color = Color(0xFF38BDF8)
                                )
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF38BDF8),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "$completedCount / $totalCount",
                                style = Typography.headlineSmall.copy(fontWeight = FontWeight.Black),
                                color = TextPrimary
                            )
                            Text(
                                text = "Habits Executed Today",
                                style = Typography.labelSmall.copy(fontSize = 10.sp),
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            val pct = if (totalCount > 0) (completedCount * 100 / totalCount) else 0
                            Text(
                                text = "$pct% protocol complete",
                                style = Typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold),
                                color = if (pct == 100) HealthGreen else Color(0xFF93C5FD)
                            )
                        }
                    }
                }
            }

            // 3. DAILY HABIT & FITNESS PROTOCOL LIST
            item {
                SoloLevelingSystemQuestWindow(
                    quests = dailyCountableQuests,
                    onIncrementReps = { quest, delta ->
                        viewModel.incrementExerciseProgress(quest, delta)
                    },
                    onEditTargetLimit = { quest ->
                        editingLimitQuest = quest
                    },
                    onVerifyProof = { quest ->
                        activeProofQuest = quest
                    }
                )
            }

            // 4. REAL-WORLD CHALLENGES & MILESTONES
            if (bossRaids.isNotEmpty()) {
                item {
                    SystemSectionHeader(
                        title = "ACTIVE MILESTONES & CHALLENGES",
                        subtitle = "Cumulative endurance and habit lock-in goals",
                        icon = Icons.Default.Flag
                    )
                }

                items(bossRaids) { milestone ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = LevelUpGold.copy(alpha = 0.4f),
                        backgroundColor = Color(0xFF161A26)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = milestone.title,
                                        style = Typography.titleMedium.copy(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = milestone.description,
                                        style = Typography.bodyMedium.copy(fontSize = 12.sp),
                                        color = TextSecondary
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(LevelUpGold.copy(alpha = 0.2f))
                                        .border(1.dp, LevelUpGold.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "${milestone.bossHp}/${milestone.maxBossHp}",
                                        style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = LevelUpGold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            HunterProgressBar(
                                current = milestone.bossHp,
                                max = milestone.maxBossHp,
                                label = "PROGRESS ACCUMULATION",
                                gradient = Brush.horizontalGradient(
                                    listOf(Color(0xFFF59E0B), Color(0xFF10B981))
                                ),
                                barHeight = 8.dp,
                                trailingText = "${(milestone.bossHp * 100 / milestone.maxBossHp.coerceAtLeast(1))}%"
                            )
                        }
                    }
                }
            }

            // 5. HOLISTIC LIFE PILLARS RADAR
            item {
                profile?.let { p ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = ShadowIndigo.copy(alpha = 0.4f),
                        backgroundColor = Color(0xFF141926)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            SystemSectionHeader(
                                title = "HOLISTIC PERFORMANCE PILLARS",
                                subtitle = "Strength, Discipline, Deep Work, Focus, Energy, and Health",
                                icon = Icons.AutoMirrored.Filled.TrendingUp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            HunterRadarChart(
                                stats = HunterStats(
                                    strength = p.strength,
                                    discipline = p.discipline,
                                    intelligence = p.intelligence,
                                    focus = p.focus,
                                    charisma = p.charisma,
                                    health = p.health
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        // Camera Proof Verification Scanner
        activeProofQuest?.let { quest ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.92f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PHOTO PROOF: ${quest.title}",
                            style = Typography.titleMedium,
                            color = NeonCyan
                        )
                        IconButton(onClick = { activeProofQuest = null }) {
                            Text("✕", color = Color.White, fontSize = 20.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    CameraProofScanner(
                        taskTitle = quest.title,
                        proofType = quest.proofType,
                        instructions = quest.proofInstructions,
                        onPhotoCaptured = { bitmap ->
                            viewModel.completeQuestWithProof(quest, bitmap)
                            activeProofQuest = null
                        }
                    )
                }
            }
        }

        // Edit Daily Quota Limit Dialog
        if (editingLimitQuest != null) {
            EditTargetLimitDialog(
                quest = editingLimitQuest!!,
                onDismiss = { editingLimitQuest = null },
                onSaveLimit = { newLimit ->
                    viewModel.updateQuestTargetLimit(editingLimitQuest!!, newLimit)
                    editingLimitQuest = null
                }
            )
        }

        // 120-Second Craving Interceptor Modal
        if (showUrgeDeflectorModal) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.9f))
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = AlertCrimson,
                    backgroundColor = Color(0xFF1E1318)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = AlertCrimson,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "120-SECOND CRAVING PAUSE",
                            style = Typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Physical cravings peak within 90-120 seconds, then rapidly decay. Execute these 3 evidence-based grounding steps:",
                            style = Typography.bodyMedium,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0x33000000), RoundedCornerShape(8.dp))
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "1. DRINK 300ml COLD WATER: Flushes dopamine receptor trigger and resets oral fixation.",
                                style = Typography.bodyMedium.copy(fontSize = 12.sp),
                                color = TextCyan
                            )
                            Text(
                                text = "2. 3 PHYSIOLOGICAL SIGHS: Two quick deep inhales through nose, one long slow exhale through mouth.",
                                style = Typography.bodyMedium.copy(fontSize = 12.sp),
                                color = TextPrimary
                            )
                            Text(
                                text = "3. 10 PUSH-UPS OR AIR SQUATS: Divert blood flow to skeletal muscles and burn cortisol.",
                                style = Typography.bodyMedium.copy(fontSize = 12.sp),
                                color = LevelUpGold
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    viewModel.sendChatMessage("I successfully deployed the 120-second craving interceptor protocol and resisted a tobacco/gutkha urge.", isExcuseOrUrge = true)
                                    showUrgeDeflectorModal = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = HealthGreen,
                                    contentColor = BackgroundVoid
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("I RESISTED (+1 CLEAN)", style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                            }

                            Button(
                                onClick = {
                                    showUrgeDeflectorModal = false
                                    onNavigateToCoach()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ShadowIndigo,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("ASK AI COACH", style = Typography.labelSmall)
                            }
                        }
                    }
                }
            }
        }

        // Profile & Account Settings Modal (For any user, not hardcoded!)
        if (showProfileSettingsModal) {
            AdminCommandCenterModal(
                profile = profile,
                onDismiss = { showProfileSettingsModal = false },
                onActivateAdmin = { name, email ->
                    viewModel.updateUserProfile(name, email, profile?.title ?: "Practitioner", profile?.preferredAiTone ?: "PERFORMANCE")
                },
                onInstantLevelUp = { levels ->
                    viewModel.adminInstantLevelUp(levels)
                }
            )
        }
    }
}
