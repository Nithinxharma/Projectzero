package com.example.ui.screens.quests

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.ui.components.BossHpBar
import com.example.ui.components.CameraProofScanner
import com.example.ui.components.EditTargetLimitDialog
import com.example.ui.components.GlassCard
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
import com.example.ui.theme.TextCyan
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.Typography
import com.example.ui.viewmodel.HunterViewModel

@Composable
fun QuestsScreen(
    viewModel: HunterViewModel,
    modifier: Modifier = Modifier
) {
    val allQuests by viewModel.allQuests.collectAsState()
    val isAiLoading by viewModel.isAiLoading.collectAsState()
    val isVerifying by viewModel.isVerifyingProof.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("ALL", "DAILY", "MAIN", "BOSS RAIDS", "COMPLETED")

    var showCreateQuestModal by remember { mutableStateOf(false) }
    var showAiQuestModal by remember { mutableStateOf(false) }
    var activeProofQuest by remember { mutableStateOf<QuestEntity?>(null) }
    var editingLimitQuest by remember { mutableStateOf<QuestEntity?>(null) }

    val filteredQuests = when (selectedTabIndex) {
        1 -> allQuests.filter { it.category == "DAILY" && !it.isCompleted }
        2 -> allQuests.filter { it.category == "MAIN" && !it.isCompleted }
        3 -> allQuests.filter { it.isBossRaid }
        4 -> allQuests.filter { it.isCompleted }
        else -> allQuests
    }

    Box(modifier = modifier.fillMaxSize().background(BackgroundVoid)) {
        SystemAmbientParticles(particleCount = 18)

        Column(modifier = Modifier.fillMaxSize()) {
            // Screen Header & Action Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "SHADOW QUEST SYSTEM",
                            style = Typography.headlineSmall,
                            color = NeonCyan
                        )
                        Text(
                            text = "Execute protocols to level up attributes",
                            style = Typography.labelSmall,
                            color = TextSecondary
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // AI Summon Quest Button
                        IconButton(
                            onClick = { showAiQuestModal = true },
                            modifier = Modifier
                                .size(40.dp)
                                .background(MonarchPurple.copy(alpha = 0.3f), CircleShape)
                                .border(1.dp, MonarchPurple, CircleShape)
                                .testTag("ai_summon_quest_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "AI Generate Quest",
                                tint = LevelUpGold
                            )
                        }

                        // Manual Quest Add Button
                        IconButton(
                            onClick = { showCreateQuestModal = true },
                            modifier = Modifier
                                .size(40.dp)
                                .background(NeonCyan.copy(alpha = 0.2f), CircleShape)
                                .border(1.dp, NeonCyan, CircleShape)
                                .testTag("add_quest_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Quest",
                                tint = NeonCyan
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Filter Tabs
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.Transparent,
                    contentColor = NeonCyan,
                    edgePadding = 0.dp,
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = NeonCyan
                        )
                    },
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = title,
                                    style = Typography.labelSmall,
                                    color = if (selectedTabIndex == index) NeonCyan else TextSecondary
                                )
                            }
                        )
                    }
                }
            }

            // Quests List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (filteredQuests.isEmpty()) {
                    item {
                        GlassCard(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "NO ACTIVE QUESTS IN THIS CATEGORY",
                                    style = Typography.titleMedium,
                                    color = TextSecondary
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Tap the AI or '+' button above to summon new missions.",
                                    style = Typography.bodyMedium,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                } else {
                    items(filteredQuests) { quest ->
                        QuestCard(
                            quest = quest,
                            onVerifyProof = { activeProofQuest = quest },
                            onCompleteManual = { viewModel.completeManualQuest(quest) },
                            onAttackBoss = { viewModel.attackBoss(quest, quest.bossDamagePerTask) },
                            onIncrementReps = { delta -> viewModel.incrementExerciseProgress(quest, delta) },
                            onEditTargetLimit = { editingLimitQuest = quest }
                        )
                    }
                }
            }
        }

        // Camera Proof Modal
        activeProofQuest?.let { quest ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.9f))
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
                            text = "CAMERA PROOF: ${quest.title}",
                            style = Typography.titleMedium,
                            color = NeonCyan
                        )
                        IconButton(onClick = { activeProofQuest = null }) {
                            Text("✕", color = Color.White, fontSize = 20.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    if (isVerifying) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(color = NeonCyan)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("AI VISION EVALUATING TASK PROOF...", color = NeonCyan)
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

        // Edit Target Limit Modal
        editingLimitQuest?.let { quest ->
            EditTargetLimitDialog(
                quest = quest,
                onDismiss = { editingLimitQuest = null },
                onSaveLimit = { newLimit ->
                    viewModel.updateQuestTargetLimit(quest, newLimit)
                }
            )
        }

        // AI Quest Generator Modal
        if (showAiQuestModal) {
            AiQuestGeneratorModal(
                isLoading = isAiLoading,
                onDismiss = { showAiQuestModal = false },
                onGenerate = { goal ->
                    viewModel.generateQuestsFromAi(goal)
                    showAiQuestModal = false
                }
            )
        }

        // Custom Quest Creator Modal
        if (showCreateQuestModal) {
            CreateCustomQuestModal(
                onDismiss = { showCreateQuestModal = false },
                onCreate = { title, desc, cat, stat, xp, proof, pType, pInstr ->
                    viewModel.createCustomQuest(
                        title = title,
                        description = desc,
                        category = cat,
                        statType = stat,
                        xpReward = xp,
                        requiresCameraProof = proof,
                        proofType = pType,
                        proofInstructions = pInstr
                    )
                    showCreateQuestModal = false
                }
            )
        }
    }
}

@Composable
fun QuestCard(
    quest: QuestEntity,
    onVerifyProof: () -> Unit,
    onCompleteManual: () -> Unit,
    onAttackBoss: () -> Unit,
    onIncrementReps: (Int) -> Unit,
    onEditTargetLimit: () -> Unit
) {
    val isBoss = quest.isBossRaid
    val isCountable = quest.isCountable
    val borderColor = when {
        quest.isCompleted -> HealthGreen.copy(alpha = 0.5f)
        isBoss -> AlertCrimson.copy(alpha = 0.7f)
        quest.requiresCameraProof -> NeonCyan.copy(alpha = 0.4f)
        else -> GlassBorder
    }

    var expandedReps by remember { mutableStateOf(false) }

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("quest_card_${quest.id}"),
        borderColor = borderColor,
        backgroundColor = if (isBoss) Color(0xDD1A0C16) else CardGlassBg
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Tag
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            when (quest.category) {
                                "MAIN" -> MonarchPurple.copy(alpha = 0.3f)
                                "BOSS" -> AlertCrimson.copy(alpha = 0.3f)
                                else -> NeonCyan.copy(alpha = 0.2f)
                            }
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = quest.category,
                        style = Typography.labelSmall,
                        color = when (quest.category) {
                            "MAIN" -> Color(0xFFE8D0FF)
                            "BOSS" -> Color(0xFFFF8A80)
                            else -> NeonCyan
                        }
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isCountable) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF090D1C))
                                .border(1.dp, if (quest.isCompleted) HealthGreen else NeonCyan, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "[${quest.currentProgress}/${quest.targetLimit} ${quest.unit}]",
                                style = Typography.labelSmall.copy(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold),
                                color = if (quest.isCompleted) HealthGreen else NeonCyan
                            )
                        }
                        IconButton(
                            onClick = onEditTargetLimit,
                            modifier = Modifier.size(24.dp).padding(start = 2.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit limit", tint = TextSecondary, modifier = Modifier.size(12.dp))
                        }
                    } else if (quest.dueDate.isNotBlank()) {
                        Text(
                            text = "DUE: ${quest.dueDate}",
                            style = Typography.labelSmall.copy(fontSize = 10.sp),
                            color = TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = quest.title,
                style = Typography.titleMedium.copy(fontSize = 15.sp),
                color = if (quest.isCompleted) TextSecondary else TextPrimary
            )

            if (quest.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = quest.description,
                    style = Typography.bodyMedium.copy(fontSize = 12.sp),
                    color = TextSecondary
                )
            }

            if (isBoss) {
                Spacer(modifier = Modifier.height(10.dp))
                BossHpBar(
                    currentHp = quest.bossHp,
                    maxHp = quest.maxBossHp,
                    bossName = quest.bossName
                )
            }

            if (isCountable && !quest.isCompleted) {
                Spacer(modifier = Modifier.height(8.dp))
                // Rep Progress Bar
                val progress = (quest.currentProgress.toFloat() / quest.targetLimit.coerceAtLeast(1).toFloat()).coerceIn(0f, 1f)
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
                            .background(Brush.horizontalGradient(listOf(NeonCyan, MonarchPurple)))
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Rep Increment Quick Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val steps = when (quest.unit) {
                        "km" -> listOf(1, 2, 5)
                        "ml" -> listOf(250, 500, 1000)
                        "mins" -> listOf(15, 30)
                        else -> listOf(5, 10, 25, 50)
                    }
                    steps.forEach { step ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0x3300F0FF))
                                .border(1.dp, NeonCyan.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                .clickable { onIncrementReps(step) }
                                .padding(vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+$step",
                                style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = NeonCyan
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "+${quest.xpReward} XP",
                        style = Typography.labelSmall,
                        color = LevelUpGold
                    )
                    Text(
                        text = "+${quest.statBonus} ${quest.statType}",
                        style = Typography.labelSmall,
                        color = NeonCyan
                    )
                }

                if (quest.isCompleted) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = HealthGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("COMPLETED", style = Typography.labelSmall, color = HealthGreen)
                    }
                } else if (isBoss) {
                    Button(
                        onClick = onAttackBoss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AlertCrimson,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("DEAL ${quest.bossDamagePerTask} DMG", style = Typography.labelSmall)
                    }
                } else if (quest.requiresCameraProof) {
                    Button(
                        onClick = onVerifyProof,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonCyan,
                            contentColor = BackgroundVoid
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("PROOF", style = Typography.labelSmall)
                    }
                } else {
                    Button(
                        onClick = onCompleteManual,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MonarchPurple,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("COMPLETE", style = Typography.labelSmall)
                    }
                }
            }
        }
    }
}

@Composable
fun AiQuestGeneratorModal(
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onGenerate: (String) -> Unit
) {
    var goalText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.88f))
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            borderColor = LevelUpGold.copy(alpha = 0.6f),
            backgroundColor = Color(0xFF141226)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = LevelUpGold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "AI SHADOW QUEST SUMMONER",
                            style = Typography.titleMedium,
                            color = LevelUpGold
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Text("✕", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Describe your goal (e.g. 'Quit vaping & drink 3L water', 'Study Kotlin Flow 2 hours daily', 'Morning workout habit'). The Hunter AI will architect tailored Main & Daily quests with camera proof rules.",
                    style = Typography.bodyMedium,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = goalText,
                    onValueChange = { goalText = it },
                    label = { Text("Enter your goal or struggle") },
                    placeholder = { Text("e.g. Quit smoking tobacco & build muscle") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = GlassBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = { onGenerate(goalText) },
                    enabled = goalText.isNotBlank() && !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LevelUpGold,
                        contentColor = BackgroundVoid
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = BackgroundVoid,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text("SUMMON QUESTS VIA AI", style = Typography.labelLarge)
                }
            }
        }
    }
}

@Composable
fun CreateCustomQuestModal(
    onDismiss: () -> Unit,
    onCreate: (String, String, String, String, Int, Boolean, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("DAILY") }
    var statType by remember { mutableStateOf("DISCIPLINE") }
    var xpReward by remember { mutableStateOf("80") }
    var requiresProof by remember { mutableStateOf(true) }
    var proofType by remember { mutableStateOf("STUDY") }
    var proofInstructions by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.88f))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            borderColor = NeonCyan.copy(alpha = 0.6f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("CREATE NEW QUEST", style = Typography.titleMedium, color = NeonCyan)
                        IconButton(onClick = onDismiss) { Text("✕", color = Color.White) }
                    }
                }

                item {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Quest Title") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = GlassBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )
                }

                item {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = GlassBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )
                }

                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("DAILY", "MAIN", "BOSS").forEach { cat ->
                            Button(
                                onClick = { category = cat },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (category == cat) NeonCyan else Color(0x3300F0FF),
                                    contentColor = if (category == cat) BackgroundVoid else Color.White
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(cat, style = Typography.labelSmall)
                            }
                        }
                    }
                }

                item {
                    Text("Stat Reward Type:", style = Typography.labelSmall, color = TextSecondary)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("STR", "DIS", "INT", "FOC", "HLT").forEach { stat ->
                            val fullName = when(stat) {
                                "STR" -> "STRENGTH"
                                "DIS" -> "DISCIPLINE"
                                "INT" -> "INTELLIGENCE"
                                "FOC" -> "FOCUS"
                                else -> "HEALTH"
                            }
                            Button(
                                onClick = { statType = fullName },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (statType == fullName) MonarchPurple else Color(0x339D4EDD),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(stat, style = Typography.labelSmall.copy(fontSize = 10.sp))
                            }
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = xpReward,
                        onValueChange = { xpReward = it },
                        label = { Text("XP Reward (e.g. 100)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = GlassBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )
                }

                item {
                    Button(
                        onClick = {
                            onCreate(
                                title.ifBlank { "Custom Protocol" },
                                description,
                                category,
                                statType,
                                xpReward.toIntOrNull() ?: 75,
                                requiresProof,
                                proofType,
                                proofInstructions
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonCyan,
                            contentColor = BackgroundVoid
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("CONFIRM & EMBED INTO SYSTEM", style = Typography.labelLarge)
                    }
                }
            }
        }
    }
}
