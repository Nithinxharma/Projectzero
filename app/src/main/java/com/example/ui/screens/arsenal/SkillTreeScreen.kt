package com.example.ui.screens.arsenal

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.SkillTreeNodeEntity
import com.example.ui.components.GlassCard
import com.example.ui.components.RankBadge
import com.example.ui.components.SystemSectionHeader
import com.example.ui.theme.AlertCrimson
import com.example.ui.theme.BackgroundSurface
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
fun SkillTreeScreen(
    viewModel: HunterViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.profile.collectAsState()
    val skills by viewModel.skills.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("PROTOCOLS", "PROFILE SETTINGS", "MILESTONES", "PRIVACY & STORAGE")

    val categories = listOf("WILLPOWER", "PHYSICAL", "MIND")
    var selectedCategory by remember { mutableIntStateOf(0) }

    // Profile form state for anyone to customize
    var editName by remember { mutableStateOf(profile?.hunterName ?: "Abhiron") }
    var editEmail by remember { mutableStateOf(profile?.userEmail ?: "sabhiron5@gmail.com") }
    var editHeadline by remember { mutableStateOf(profile?.title ?: "High-Performance Practitioner") }
    var editTone by remember { mutableStateOf(profile?.preferredAiTone ?: "PERFORMANCE") }

    Box(modifier = modifier.fillMaxSize().background(BackgroundVoid)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
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
                            text = "SELF-MASTERY & PROTOCOLS",
                            style = Typography.headlineSmall,
                            color = NeonCyan
                        )
                        Text(
                            text = "Behavioral science principles & personal configuration",
                            style = Typography.labelSmall,
                            color = TextSecondary
                        )
                    }

                    profile?.let { p ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(ShadowIndigo.copy(alpha = 0.25f))
                                .border(1.dp, ShadowIndigo, RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "STREAK: ${p.currentStreak} DAYS",
                                style = Typography.labelSmall,
                                color = LevelUpGold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

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

            // Tab Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when (selectedTabIndex) {
                    0 -> {
                        // 1. BEHAVIORAL PROTOCOLS TAB
                        item {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(categories.size) { idx ->
                                    val isSelected = selectedCategory == idx
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSelected) ShadowIndigo else BackgroundSurface)
                                            .border(1.dp, if (isSelected) NeonCyan else GlassBorder, RoundedCornerShape(8.dp))
                                            .clickable { selectedCategory = idx }
                                            .padding(horizontal = 12.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = categories[idx],
                                            style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = if (isSelected) Color.White else TextSecondary
                                        )
                                    }
                                }
                            }
                        }

                        val activeCat = categories[selectedCategory]
                        val categorySkills = skills.filter { it.category == activeCat }

                        items(categorySkills) { skill ->
                            SkillCard(
                                skill = skill,
                                canUnlock = (profile?.availableSkillPoints ?: 0) >= skill.costPoints,
                                onUnlock = { viewModel.unlockSkill(skill) }
                            )
                        }
                    }

                    1 -> {
                        // 2. PROFILE SETTINGS TAB (For ANY person, not hardcoded!)
                        item {
                            SystemSectionHeader(
                                title = "CUSTOMIZE YOUR PROFILE",
                                subtitle = "Configure your name, email, headline, and coaching tone",
                                icon = Icons.Default.ManageAccounts
                            )
                        }

                        item {
                            GlassCard(modifier = Modifier.fillMaxWidth()) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    OutlinedTextField(
                                        value = editName,
                                        onValueChange = { editName = it },
                                        label = { Text("Display Name") },
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = NeonCyan,
                                            unfocusedBorderColor = GlassBorder,
                                            focusedTextColor = TextPrimary,
                                            unfocusedTextColor = TextPrimary
                                        )
                                    )

                                    OutlinedTextField(
                                        value = editEmail,
                                        onValueChange = { editEmail = it },
                                        label = { Text("Email Address") },
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = NeonCyan,
                                            unfocusedBorderColor = GlassBorder,
                                            focusedTextColor = TextPrimary,
                                            unfocusedTextColor = TextPrimary
                                        )
                                    )

                                    OutlinedTextField(
                                        value = editHeadline,
                                        onValueChange = { editHeadline = it },
                                        label = { Text("Profession / Personal Focus") },
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = NeonCyan,
                                            unfocusedBorderColor = GlassBorder,
                                            focusedTextColor = TextPrimary,
                                            unfocusedTextColor = TextPrimary
                                        )
                                    )

                                    Text("AI Coaching Tone:", style = Typography.labelMedium, color = TextSecondary)

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        listOf(
                                            "PERFORMANCE" to "Performance",
                                            "SCIENTIFIC" to "Scientific",
                                            "EMPATHETIC" to "Mindful"
                                        ).forEach { (toneKey, toneLabel) ->
                                            val isSelected = editTone == toneKey
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(if (isSelected) ShadowIndigo else BackgroundSurface)
                                                    .border(1.dp, if (isSelected) NeonCyan else GlassBorder, RoundedCornerShape(6.dp))
                                                    .clickable { editTone = toneKey }
                                                    .padding(vertical = 8.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = toneLabel,
                                                    style = Typography.labelSmall.copy(fontSize = 11.sp),
                                                    color = if (isSelected) Color.White else TextSecondary
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Button(
                                        onClick = {
                                            viewModel.updateUserProfile(editName, editEmail, editHeadline, editTone)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = NeonCyan,
                                            contentColor = BackgroundVoid
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text("SAVE CHANGES", style = Typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                                    }
                                }
                            }
                        }
                    }

                    2 -> {
                        // 3. DISCIPLINE MILESTONES TAB
                        item {
                            SystemSectionHeader(
                                title = "DISCIPLINE MILESTONES",
                                subtitle = "Evidence-based habit formation stages",
                                icon = Icons.Default.EmojiEvents
                            )
                        }

                        val milestonesList = listOf(
                            Triple("14-Day Tobacco & Smoke Free", "Baseline dopamine receptors restored. Cravings decay rapidly.", (profile?.tobaccoFreeDays ?: 0) >= 14),
                            Triple("7-Day Consistent Protocol", "Completed all daily physical & cognitive targets for 7 days.", (profile?.currentStreak ?: 0) >= 7),
                            Triple("100km Monthly Cardio Club", "Accumulated aerobic endurance volume for cardiovascular longevity.", false),
                            Triple("25 Deep Focus Sessions", "Verified distraction-free deep work blocks with photo validation.", false),
                            Triple("30-Day Total Transformation", "The 30-day neuroplasticity threshold for lifelong habit identity.", (profile?.highestStreak ?: 0) >= 30)
                        )

                        items(milestonesList) { (titleName, desc, unlocked) ->
                            GlassCard(
                                modifier = Modifier.fillMaxWidth(),
                                borderColor = if (unlocked) LevelUpGold.copy(alpha = 0.6f) else Color(0x22FFFFFF),
                                backgroundColor = if (unlocked) Color(0xFF1B2030) else CardGlassBg
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                        Icon(
                                            imageVector = if (unlocked) Icons.Default.Verified else Icons.Default.Lock,
                                            contentDescription = null,
                                            tint = if (unlocked) LevelUpGold else TextSecondary,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(
                                                text = titleName,
                                                style = Typography.titleMedium.copy(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                                                color = if (unlocked) LevelUpGold else TextSecondary
                                            )
                                            Text(
                                                text = desc,
                                                style = Typography.bodyMedium.copy(fontSize = 12.sp),
                                                color = TextSecondary
                                            )
                                        }
                                    }

                                    if (unlocked) {
                                        Text("ACHIEVED", style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = HealthGreen)
                                    }
                                }
                            }
                        }
                    }

                    3 -> {
                        // 4. DATA PRIVACY & STORAGE TAB
                        item {
                            SystemSectionHeader(
                                title = "LOCAL-FIRST PRIVACY & ARCHITECTURE",
                                subtitle = "100% Private SQLite storage on your device",
                                icon = Icons.Default.Security
                            )
                        }

                        item {
                            GlassCard(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Text("• Architecture: 100% Local-first with Android Room Database", style = Typography.bodyMedium, color = TextPrimary)
                                    Text("• Camera Proofs: Stored securely in private application sandbox cache", style = Typography.bodyMedium, color = TextPrimary)
                                    Text("• AI Processing: Gemini API evaluates proof securely via HTTPS", style = Typography.bodyMedium, color = TextPrimary)
                                    Text("• No Ad Tracking: Zero telemetry, zero analytics tracking", style = Typography.bodyMedium, color = TextPrimary)
                                    Text("• Offline Resilient: All habit logs persist without internet", style = Typography.labelSmall, color = HealthGreen)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SkillCard(
    skill: SkillTreeNodeEntity,
    canUnlock: Boolean,
    onUnlock: () -> Unit
) {
    val isUnlocked = skill.isUnlocked

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("skill_card_${skill.id}"),
        borderColor = if (isUnlocked) HealthGreen.copy(alpha = 0.5f) else GlassBorder
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (isUnlocked) HealthGreen.copy(alpha = 0.2f) else BackgroundSurface)
                        .border(1.dp, if (isUnlocked) HealthGreen else Color(0x33FFFFFF), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isUnlocked) Icons.Default.Check else Icons.Default.Lock,
                        contentDescription = null,
                        tint = if (isUnlocked) HealthGreen else TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = skill.name,
                        style = Typography.titleMedium.copy(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                        color = if (isUnlocked) TextPrimary else TextSecondary
                    )
                    Text(
                        text = skill.description,
                        style = Typography.bodyMedium.copy(fontSize = 12.sp),
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Effect: ${skill.statBoostDescription}",
                        style = Typography.labelSmall.copy(fontSize = 11.sp),
                        color = NeonCyan
                    )
                }
            }

            if (isUnlocked) {
                Text(
                    text = "ACTIVE",
                    style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = HealthGreen
                )
            } else {
                Button(
                    onClick = onUnlock,
                    enabled = canUnlock,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ShadowIndigo,
                        contentColor = Color.White,
                        disabledContainerColor = Color(0x22FFFFFF),
                        disabledContentColor = TextSecondary
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("ACTIVATE", style = Typography.labelSmall)
                }
            }
        }
    }
}
