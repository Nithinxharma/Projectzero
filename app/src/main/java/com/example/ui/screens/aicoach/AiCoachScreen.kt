package com.example.ui.screens.aicoach

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassCard
import com.example.ui.components.SystemAmbientParticles
import com.example.ui.components.SystemSectionHeader
import com.example.ui.theme.AlertCrimson
import com.example.ui.theme.BackgroundVoid
import com.example.ui.theme.CardGlassBg
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
fun AiCoachScreen(
    viewModel: HunterViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.profile.collectAsState()
    val chatMessages by viewModel.chatMessages.collectAsState()
    val dailyLogs by viewModel.dailyLogs.collectAsState()
    val isAiLoading by viewModel.isAiLoading.collectAsState()

    var activeTab by remember { mutableIntStateOf(0) } // 0 = Hunter AI Mentor, 1 = Daily Life Logger & Reports
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    val tones = listOf(
        Pair("SYSTEM", "Cold System AI"),
        Pair("IRON_COACH", "Iron Coach (No Excuses)"),
        Pair("WISE_MENTOR", "Wise Grandmaster")
    )
    val currentTone = profile?.preferredAiTone ?: "SYSTEM"

    Box(modifier = modifier.fillMaxSize().background(BackgroundVoid)) {
        SystemAmbientParticles(particleCount = 18)

        Column(modifier = Modifier.fillMaxSize()) {
            // Header & Tab Selector
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
                            text = "AI SYSTEM LIFE COACH",
                            style = Typography.headlineSmall,
                            color = NeonCyan
                        )
                        Text(
                            text = "Powered by Gemini • Accountability & Diagnostics",
                            style = Typography.labelSmall,
                            color = TextSecondary
                        )
                    }

                    // Mode Switcher
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0x3300F0FF))
                            .padding(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (activeTab == 0) NeonCyan else Color.Transparent)
                                .clickable { activeTab = 0 }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "MENTOR",
                                style = Typography.labelSmall,
                                color = if (activeTab == 0) BackgroundVoid else TextSecondary
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (activeTab == 1) NeonCyan else Color.Transparent)
                                .clickable { activeTab = 1 }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "LOG & REPORT",
                                style = Typography.labelSmall,
                                color = if (activeTab == 1) BackgroundVoid else TextSecondary
                            )
                        }
                    }
                }
            }

            if (activeTab == 0) {
                // 1. AI MENTOR CONVERSATION TAB
                Column(modifier = Modifier.fillMaxSize()) {
                    // Tone Switcher Chips
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(tones) { (key, label) ->
                            val isSelected = currentTone == key
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) MonarchPurple.copy(alpha = 0.4f) else Color(0x3312182B))
                                    .border(1.dp, if (isSelected) NeonCyan else Color(0x3300F0FF), RoundedCornerShape(8.dp))
                                    .clickable { viewModel.updateAiTone(key) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = label,
                                    style = Typography.labelSmall,
                                    color = if (isSelected) NeonCyan else TextSecondary
                                )
                            }
                        }
                    }

                    // Quick Prompt Actions
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            QuickPill(
                                label = "🚨 Urge Attack (Craving)",
                                color = AlertCrimson,
                                onClick = {
                                    viewModel.sendChatMessage("I am experiencing an intense tobacco/gutkha urge right now. Guide me through the urge deflector protocol.", isExcuseOrUrge = true)
                                }
                            )
                        }
                        item {
                            QuickPill(
                                label = "⚡ Procrastination Buster",
                                color = LevelUpGold,
                                onClick = {
                                    viewModel.sendChatMessage("I am procrastinating on my study and workout. Call me out and give me a 2-minute starter mission.", isExcuseOrUrge = true)
                                }
                            )
                        }
                        item {
                            QuickPill(
                                label = "📊 Stats Diagnostics",
                                color = NeonCyan,
                                onClick = {
                                    viewModel.sendChatMessage("Analyze my current Hunter attributes, discipline score, and burnout risk. What should I prioritize?", isExcuseOrUrge = false)
                                }
                            )
                        }
                    }

                    // Messages Stream
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(chatMessages) { msg ->
                            val isUser = msg.sender == "USER"
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (isUser) MonarchPurple.copy(alpha = 0.35f)
                                            else Color(0xDD12182B)
                                        )
                                        .border(
                                            1.dp,
                                            if (isUser) MonarchPurple else NeonCyan.copy(alpha = 0.4f),
                                            RoundedCornerShape(12.dp)
                                        )
                                        .padding(12.dp)
                                        .fillMaxWidth(0.85f)
                                ) {
                                    Column {
                                        Text(
                                            text = if (isUser) "PLAYER" else "【 SYSTEM AI COACH 】",
                                            style = Typography.labelSmall.copy(fontSize = 10.sp),
                                            color = if (isUser) Color(0xFFE8D0FF) else NeonCyan
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = msg.message,
                                            style = Typography.bodyMedium,
                                            color = TextPrimary
                                        )
                                    }
                                }
                            }
                        }

                        if (isAiLoading) {
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    CircularProgressIndicator(
                                        color = NeonCyan,
                                        modifier = Modifier.size(18.dp),
                                        strokeWidth = 2.dp
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "System AI evaluating response...",
                                        style = Typography.labelSmall,
                                        color = NeonCyan
                                    )
                                }
                            }
                        }
                    }

                    // Input Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0A0C16))
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            placeholder = { Text("Ask your Hunter Mentor...", style = Typography.bodyMedium) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("coach_input_field"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonCyan,
                                unfocusedBorderColor = Color(0x3300F0FF),
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = {
                                if (inputText.isNotBlank()) {
                                    viewModel.sendChatMessage(inputText)
                                    inputText = ""
                                }
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .background(NeonCyan, CircleShape)
                                .testTag("send_coach_msg_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                tint = BackgroundVoid
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(72.dp))
                }
            } else {
                // 2. DAILY LIFE LOGGER & REPORTS TAB
                DailyLifeLoggerTab(
                    viewModel = viewModel,
                    isAiLoading = isAiLoading,
                    latestLog = dailyLogs.firstOrNull()
                )
            }
        }
    }
}

@Composable
fun DailyLifeLoggerTab(
    viewModel: HunterViewModel,
    isAiLoading: Boolean,
    latestLog: com.example.data.local.entity.DailyLifeLogEntity?
) {
    var sleepHours by remember { mutableFloatStateOf(7.5f) }
    var exerciseMins by remember { mutableIntStateOf(30) }
    var studyMins by remember { mutableIntStateOf(60) }
    var workMins by remember { mutableIntStateOf(360) }
    var screenTimeMins by remember { mutableIntStateOf(180) }
    var tobaccoResisted by remember { mutableIntStateOf(3) }
    var tobaccoLapses by remember { mutableIntStateOf(0) }
    var waterMl by remember { mutableIntStateOf(2500) }
    var steps by remember { mutableIntStateOf(6000) }
    var mood by remember { mutableIntStateOf(4) }
    var energy by remember { mutableIntStateOf(4) }
    var notes by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // AI Generated Report Banner if exists
        latestLog?.let { log ->
            if (log.aiDailyReport.isNotBlank()) {
                item {
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = LevelUpGold.copy(alpha = 0.6f),
                        backgroundColor = Color(0xDD18142E)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Analytics,
                                    contentDescription = null,
                                    tint = LevelUpGold,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "LATEST SYSTEM DIAGNOSTIC REPORT",
                                    style = Typography.titleMedium,
                                    color = LevelUpGold
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = log.aiDailyReport,
                                style = Typography.bodyMedium,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }
        }

        item {
            SystemSectionHeader(
                title = "LOG TODAY'S LIFE METRICS",
                subtitle = "The System monitors physical & cognitive fatigue",
                icon = Icons.Default.Psychology
            )
        }

        // Sliders & Number Controls
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Sleep
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Sleep: ${"%.1f".format(sleepHours)} Hours", style = Typography.labelMedium, color = TextPrimary)
                    }
                    Slider(
                        value = sleepHours,
                        onValueChange = { sleepHours = it },
                        valueRange = 3f..12f,
                        steps = 17,
                        colors = SliderDefaults.colors(thumbColor = NeonCyan, activeTrackColor = NeonCyan)
                    )

                    // Exercise
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Exercise: $exerciseMins Min", style = Typography.labelMedium, color = TextPrimary)
                        Text("Study/Work: ${studyMins}m / ${workMins}m", style = Typography.labelSmall, color = TextSecondary)
                    }
                    Slider(
                        value = exerciseMins.toFloat(),
                        onValueChange = { exerciseMins = it.toInt() },
                        valueRange = 0f..180f,
                        colors = SliderDefaults.colors(thumbColor = MonarchPurple, activeTrackColor = MonarchPurple)
                    )

                    // Tobacco Urges Resisted vs Lapses
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Tobacco/Gutkha Urges Resisted: $tobaccoResisted", style = Typography.labelMedium, color = HealthGreen)
                        Text("Lapses: $tobaccoLapses", style = Typography.labelSmall, color = if (tobaccoLapses > 0) AlertCrimson else HealthGreen)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { tobaccoResisted++ },
                            colors = ButtonDefaults.buttonColors(containerColor = HealthGreen.copy(alpha = 0.2f), contentColor = HealthGreen),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("+1 RESISTED", style = Typography.labelSmall)
                        }
                        Button(
                            onClick = { tobaccoLapses++ },
                            colors = ButtonDefaults.buttonColors(containerColor = AlertCrimson.copy(alpha = 0.2f), contentColor = AlertCrimson),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("+1 LAPSE", style = Typography.labelSmall)
                        }
                    }

                    // Water & Steps
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Water: ${waterMl}ml", style = Typography.labelMedium, color = NeonCyan)
                        Text("Steps: $steps", style = Typography.labelMedium, color = LevelUpGold)
                    }
                    Slider(
                        value = waterMl.toFloat(),
                        onValueChange = { waterMl = it.toInt() },
                        valueRange = 500f..5000f,
                        colors = SliderDefaults.colors(thumbColor = NeonCyan, activeTrackColor = NeonCyan)
                    )

                    // Notes
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Daily Hunter Log & Reflections") },
                        placeholder = { Text("Notes on energy, triggers, or accomplishments...") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = Color(0x3300F0FF),
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = {
                            viewModel.saveDailyLifeLog(
                                sleepHours = sleepHours,
                                exerciseMins = exerciseMins,
                                studyMins = studyMins,
                                workMins = workMins,
                                screenTimeMins = screenTimeMins,
                                tobaccoResisted = tobaccoResisted,
                                tobaccoLapses = tobaccoLapses,
                                mood = mood,
                                energy = energy,
                                waterMl = waterMl,
                                steps = steps,
                                note = notes
                            )
                        },
                        enabled = !isAiLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LevelUpGold,
                            contentColor = BackgroundVoid
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("generate_report_btn")
                    ) {
                        if (isAiLoading) {
                            CircularProgressIndicator(
                                color = BackgroundVoid,
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text("GENERATE AI HUNTER REPORT", style = Typography.labelLarge)
                    }
                }
            }
        }
    }
}

@Composable
fun QuickPill(
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.18f))
            .border(1.dp, color.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(text = label, style = Typography.labelSmall, color = color)
    }
}
