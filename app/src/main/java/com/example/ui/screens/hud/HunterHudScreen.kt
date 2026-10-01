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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Shield
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
import com.example.ui.components.BossHpBar
import com.example.ui.components.CameraProofScanner
import com.example.ui.components.EditTargetLimitDialog
import com.example.ui.components.GlassCard
import com.example.ui.components.HunterHpBar
import com.example.ui.components.HunterRadarChart
import com.example.ui.components.HunterStats
import com.example.ui.components.HunterXpBar
import com.example.ui.components.RankBadge
import com.example.ui.components.SoloLevelingSystemQuestWindow
import com.example.ui.components.SystemAmbientParticles
import com.example.ui.components.SystemSectionHeader
import com.example.ui.theme.AlertCrimson
import com.example.ui.theme.BackgroundVoid
import com.example.ui.theme.CardGlassBg
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.LevelUpGold
import com.example.ui.theme.MonarchPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ShadowIndigo
import com.example.ui.theme.TextCyan
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
    var showAdminModal by remember { mutableStateOf(false) }

    val dailyCountableQuests = allQuests.filter { it.category == "DAILY" }
    val activeBoss = bossRaids.firstOrNull { !it.isCompleted }

    Box(modifier = modifier.fillMaxSize().background(BackgroundVoid)) {
        // Ambient system particles
        SystemAmbientParticles(particleCount = 24)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ADMIN / SYSTEM CREATOR STATUS BAR
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF3B007A), Color(0xFF6B0024), Color(0xFF003853))
                            )
                        )
                        .border(1.dp, LevelUpGold, RoundedCornerShape(8.dp))
                        .clickable { showAdminModal = true }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("admin_status_bar")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "👑 SYSTEM ADMIN : sabhiron5@gmail.com",
                                style = Typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                color = LevelUpGold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(HealthGreen.copy(alpha = 0.25f))
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text("GOD MODE", style = Typography.labelSmall.copy(fontSize = 8.sp, color = HealthGreen))
                            }
                        }

                        Text(
                            text = "COMMANDS ⚙",
                            style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = NeonCyan
                        )
                    }
                }
            }

            // 1. HUNTER PROFILE & RANK STATUS HUD
            item {
                profile?.let { p ->
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("hunter_hud_card"),
                        borderColor = if (p.isAdmin) LevelUpGold.copy(alpha = 0.7f) else NeonCyan.copy(alpha = 0.5f),
                        borderWidth = 1.5.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(52.dp)
                                            .clip(CircleShape)
                                            .background(
                                                Brush.radialGradient(
                                                    listOf(LevelUpGold, MonarchPurple)
                                                )
                                            )
                                            .border(2.dp, LevelUpGold, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "👑",
                                            style = Typography.headlineSmall.copy(
                                                color = Color.White,
                                                fontWeight = FontWeight.Black
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
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(3.dp))
                                                    .background(LevelUpGold.copy(alpha = 0.2f))
                                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                                            ) {
                                                Text("ADMIN", style = Typography.labelSmall.copy(fontSize = 9.sp, color = LevelUpGold))
                                            }
                                        }
                                        Text(
                                            text = "TITLE: ${p.title.uppercase()}",
                                            style = Typography.labelSmall,
                                            color = LevelUpGold
                                        )
                                    }
                                }

                                RankBadge(rank = p.rank, showTitle = false)
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // XP Progress Bar
                            HunterXpBar(
                                currentXp = p.currentXp,
                                maxXp = p.maxXp,
                                level = p.level
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // HP Bar
                            HunterHpBar(
                                currentHp = p.hp,
                                maxHp = p.maxHp
                            )
                        }
                    }
                }
            }

            // 2. SOLO LEVELING SYSTEM QUEST WINDOW (0 TO N EXERCISE REPS & LIMITS)
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

            // 3. HUNTER VITALS & STREAK MATRIX
            item {
                profile?.let { p ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Streak Vitals
                        GlassCard(
                            modifier = Modifier.weight(1f),
                            backgroundColor = Color(0x9912182B)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocalFireDepartment,
                                        contentDescription = null,
                                        tint = AlertCrimson,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "STREAK",
                                        style = Typography.labelSmall,
                                        color = TextSecondary
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${p.currentStreak} DAYS",
                                    style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Best: ${p.highestStreak}d",
                                    style = Typography.labelSmall.copy(fontSize = 10.sp),
                                    color = LevelUpGold
                                )
                            }
                        }

                        // Discipline / Clean days
                        GlassCard(
                            modifier = Modifier.weight(1f),
                            backgroundColor = Color(0x9912182B)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Shield,
                                        contentDescription = null,
                                        tint = HealthGreen,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "CLEAN PROTOCOL",
                                        style = Typography.labelSmall,
                                        color = TextSecondary
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${p.tobaccoFreeDays} DAYS",
                                    style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = HealthGreen
                                )
                                Text(
                                    text = "Tobacco / Urge Free",
                                    style = Typography.labelSmall.copy(fontSize = 10.sp),
                                    color = TextSecondary
                                )
                            }
                        }

                        // Consistency Index
                        GlassCard(
                            modifier = Modifier.weight(1f),
                            backgroundColor = Color(0x9912182B)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.ElectricBolt,
                                        contentDescription = null,
                                        tint = NeonCyan,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "DISCIPLINE",
                                        style = Typography.labelSmall,
                                        color = TextSecondary
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${p.consistencyScore}%",
                                    style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = NeonCyan
                                )
                                Text(
                                    text = "Burnout: ${p.burnoutRisk}",
                                    style = Typography.labelSmall.copy(fontSize = 10.sp),
                                    color = if (p.burnoutRisk == "Low") HealthGreen else AlertCrimson
                                )
                            }
                        }
                    }
                }
            }

            // 4. EMERGENCY URGE / CRAVING INTERCEPTOR BUTTON
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF700021), Color(0xFF3B007A))
                            )
                        )
                        .border(1.5.dp, AlertCrimson.copy(alpha = 0.8f), RoundedCornerShape(14.dp))
                        .clickable { showUrgeDeflectorModal = true }
                        .padding(14.dp)
                        .testTag("emergency_urge_button")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(AlertCrimson.copy(alpha = 0.3f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = AlertCrimson,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "URGE / PROCRASTINATION ATTACK",
                                    style = Typography.titleMedium.copy(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = Color.White
                                )
                                Text(
                                    text = "Tap to activate 120s Urge Deflector & gain Willpower XP",
                                    style = Typography.labelSmall,
                                    color = Color(0xFFFFD0D8)
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = LevelUpGold,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // 5. ACTIVE BOSS RAID GATE CARD
            item {
                if (activeBoss != null) {
                    SystemSectionHeader(
                        title = "ACTIVE BOSS RAID GATE",
                        subtitle = "Deal damage by completing daily discipline quests",
                        icon = Icons.Default.Shield,
                        badgeText = "HIGH REWARD"
                    )

                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = AlertCrimson.copy(alpha = 0.6f),
                        backgroundColor = Color(0xCC200F18)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = activeBoss.title.uppercase(),
                                        style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color(0xFFFF8A80)
                                    )
                                    Text(
                                        text = activeBoss.description,
                                        style = Typography.bodyMedium,
                                        color = TextSecondary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            BossHpBar(
                                currentHp = activeBoss.bossHp,
                                maxHp = activeBoss.maxBossHp,
                                bossName = activeBoss.bossName
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "REWARD: ${activeBoss.bossRewardTitle} • +${activeBoss.xpReward} XP",
                                    style = Typography.labelSmall,
                                    color = LevelUpGold
                                )

                                Button(
                                    onClick = {
                                        viewModel.attackBoss(activeBoss, activeBoss.bossDamagePerTask)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = AlertCrimson,
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.testTag("strike_boss_btn")
                                ) {
                                    Text("STRIKE (${activeBoss.bossDamagePerTask} DMG)", style = Typography.labelSmall)
                                }
                            }
                        }
                    }
                }
            }

            // 6. HUNTER STATS RADAR PENTAGON
            item {
                profile?.let { p ->
                    SystemSectionHeader(
                        title = "HUNTER STATS MATRIX",
                        subtitle = "Direct real-life attribute mapping",
                        icon = Icons.Default.Psychology
                    )

                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        HunterRadarChart(
                            stats = HunterStats(
                                strength = p.strength,
                                discipline = p.discipline,
                                intelligence = p.intelligence,
                                focus = p.focus,
                                charisma = p.charisma,
                                health = p.health
                            )
                        )
                    }
                }
            }
        }

        // Live In-App Camera Proof Modal Scanner
        activeProofQuest?.let { quest ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.88f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PROOF EVALUATION: ${quest.proofType}",
                            style = Typography.titleMedium,
                            color = NeonCyan
                        )
                        IconButton(onClick = { activeProofQuest = null }) {
                            Text("✕", color = Color.White, fontSize = 20.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (isVerifying) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                color = NeonCyan,
                                modifier = Modifier.size(54.dp),
                                strokeWidth = 4.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "SYSTEM VISION ANALYZING PHOTO...",
                                style = Typography.labelLarge,
                                color = NeonCyan
                            )
                            Text(
                                text = "Evaluating task verification criteria & objects",
                                style = Typography.labelSmall,
                                color = TextSecondary
                            )
                        }
                    } else {
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
        }

        // Target Limit Customizer Modal
        editingLimitQuest?.let { quest ->
            EditTargetLimitDialog(
                quest = quest,
                onDismiss = { editingLimitQuest = null },
                onSaveLimit = { newLimit ->
                    viewModel.updateQuestTargetLimit(quest, newLimit)
                }
            )
        }

        // Urge Deflector Protocol Modal
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
                    backgroundColor = Color(0xFF140811)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = AlertCrimson,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "URGE INTERCEPTOR ACTIVE",
                            style = Typography.titleLarge,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Cravings peak for 120 seconds then rapidly decay. Execute these 3 emergency grounding steps:",
                            style = Typography.bodyMedium,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0x33000000), RoundedCornerShape(8.dp))
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "1. DRINK 300ml COLD WATER: Flush out dopamine trigger.",
                                style = Typography.labelSmall,
                                color = TextCyan
                            )
                            Text(
                                text = "2. 5 BOX BREATHS: Inhale 4s, Hold 4s, Exhale 6s.",
                                style = Typography.labelSmall,
                                color = TextPrimary
                            )
                            Text(
                                text = "3. 10 PUSHUPS / 30s SQUATS: Divert bloodflow to muscles.",
                                style = Typography.labelSmall,
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
                                    viewModel.sendChatMessage("I had a strong tobacco/gutkha urge and successfully deployed the Urge Interceptor protocol.", isExcuseOrUrge = true)
                                    showUrgeDeflectorModal = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = HealthGreen,
                                    contentColor = BackgroundVoid
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("I RESISTED (+25 XP)", style = Typography.labelSmall)
                            }

                            Button(
                                onClick = {
                                    showUrgeDeflectorModal = false
                                    onNavigateToCoach()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MonarchPurple,
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

        // Admin Command Center Modal
        if (showAdminModal) {
            AdminCommandCenterModal(
                profile = profile,
                onDismiss = { showAdminModal = false },
                onActivateAdmin = { name, email ->
                    viewModel.setAdminAuthority(name, email, true)
                },
                onInstantLevelUp = { levels ->
                    viewModel.adminInstantLevelUp(levels)
                }
            )
        }
    }
}
