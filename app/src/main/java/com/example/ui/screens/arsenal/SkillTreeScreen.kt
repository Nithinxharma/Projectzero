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
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
fun SkillTreeScreen(
    viewModel: HunterViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.profile.collectAsState()
    val skills by viewModel.skills.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("SKILL TREE", "RANK LADDER", "TITLES", "PRIVACY & LOCAL DB")

    val categories = listOf("WILLPOWER", "PHYSICAL", "MENTAL", "KNOWLEDGE")
    var selectedCategory by remember { mutableIntStateOf(0) }

    Box(modifier = modifier.fillMaxSize().background(BackgroundVoid)) {
        SystemAmbientParticles(particleCount = 18)

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
                            text = "HUNTER ARSENAL & SKILLS",
                            style = Typography.headlineSmall,
                            color = NeonCyan
                        )
                        Text(
                            text = "Permanent passive & active psychological buffs",
                            style = Typography.labelSmall,
                            color = TextSecondary
                        )
                    }

                    profile?.let { p ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MonarchPurple.copy(alpha = 0.3f))
                                .border(1.dp, MonarchPurple, RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "POINTS: ${p.availableSkillPoints}",
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when (selectedTabIndex) {
                    0 -> {
                        // 1. SKILL TREE TAB
                        item {
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(categories.size) { idx ->
                                    val cat = categories[idx]
                                    val isSelected = selectedCategory == idx
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSelected) NeonCyan.copy(alpha = 0.25f) else Color(0x3312182B))
                                            .border(1.dp, if (isSelected) NeonCyan else Color(0x3300F0FF), RoundedCornerShape(8.dp))
                                            .clickable { selectedCategory = idx }
                                            .padding(horizontal = 12.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = cat,
                                            style = Typography.labelSmall,
                                            color = if (isSelected) NeonCyan else TextSecondary
                                        )
                                    }
                                }
                            }
                        }

                        val filteredSkills = skills.filter { it.category.equals(categories[selectedCategory], ignoreCase = true) }

                        items(filteredSkills) { skill ->
                            SkillCard(
                                skill = skill,
                                canUnlock = (profile?.availableSkillPoints ?: 0) >= skill.costPoints,
                                onUnlock = { viewModel.unlockSkill(skill) }
                            )
                        }
                    }

                    1 -> {
                        // 2. RANK LADDER TAB
                        item {
                            SystemSectionHeader(
                                title = "SOLO LEVELING RANK ASCENSION",
                                subtitle = "Your real-life discipline dictates your rank tier",
                                icon = Icons.Default.WorkspacePremium
                            )
                        }

                        val rankTiers = listOf(
                            Triple("MONARCH", "Level 50+", "Absolute Sovereign of Discipline • Unlocks all Shadow Domains"),
                            Triple("NATIONAL", "Level 35 - 49", "National Level Hunter • Unshakeable consistency & physical mastery"),
                            Triple("S", "Level 25 - 34", "S-Rank Awakened • Top 1% habit execution & zero relapse tolerance"),
                            Triple("A", "Level 18 - 24", "A-Rank Hunter • 30+ day streaks, advanced deep work & stamina"),
                            Triple("B", "Level 12 - 17", "B-Rank Hunter • Reliable daily routines, solid physical fitness"),
                            Triple("C", "Level 7 - 11", "C-Rank Hunter • Overcoming initial resistance & consistency slumps"),
                            Triple("D", "Level 4 - 6", "D-Rank Hunter • Building foundational discipline & proof habits"),
                            Triple("E", "Level 1 - 3", "E-Rank Awakened • Zero point start. The journey begins here.")
                        )

                        items(rankTiers) { (rank, req, desc) ->
                            val isCurrent = profile?.rank?.equals(rank, ignoreCase = true) == true
                            GlassCard(
                                modifier = Modifier.fillMaxWidth(),
                                borderColor = if (isCurrent) NeonCyan else GlassBorder,
                                backgroundColor = if (isCurrent) Color(0xDD1B233F) else CardGlassBg
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                        RankBadge(rank = rank)
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(
                                                text = req,
                                                style = Typography.labelSmall,
                                                color = LevelUpGold
                                            )
                                            Text(
                                                text = desc,
                                                style = Typography.bodyMedium.copy(fontSize = 12.sp),
                                                color = TextSecondary
                                            )
                                        }
                                    }
                                    if (isCurrent) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(NeonCyan.copy(alpha = 0.2f))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text("CURRENT", style = Typography.labelSmall, color = NeonCyan)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    2 -> {
                        // 3. TITLES & ACHIEVEMENTS TAB
                        item {
                            SystemSectionHeader(
                                title = "HUNTER TITLES & FEATS",
                                subtitle = "Unlocked through boss battle victories & long streaks",
                                icon = Icons.Default.EmojiEvents
                            )
                        }

                        val titlesList = listOf(
                            Triple("The Awakened Zero", "Default Title upon joining Project Zero", true),
                            Triple("Addiction Slayer", "Defeat the Smoke Fiend Boss (3+ Days Tobacco/Urge Free)", (profile?.tobaccoFreeDays ?: 0) >= 3),
                            Triple("Iron Will", "Maintain a 7-day habit completion streak", (profile?.highestStreak ?: 0) >= 7),
                            Triple("Monarch of Willpower", "Defeat Baran, Demon King of Sloth (5-Day All-Clear)", false),
                            Triple("Deep Work Scholar", "Complete 20 Deep Study sessions with camera proof", false),
                            Triple("Shadow Sovereign", "Ascend to Monarch Rank (Level 50)", (profile?.level ?: 1) >= 50)
                        )

                        items(titlesList) { (titleName, desc, unlocked) ->
                            GlassCard(
                                modifier = Modifier.fillMaxWidth(),
                                borderColor = if (unlocked) LevelUpGold.copy(alpha = 0.6f) else Color(0x22FFFFFF)
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
                                            imageVector = if (unlocked) Icons.Default.Star else Icons.Default.Lock,
                                            contentDescription = null,
                                            tint = if (unlocked) LevelUpGold else TextSecondary,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(
                                                text = titleName,
                                                style = Typography.titleMedium.copy(fontSize = 14.sp),
                                                color = if (unlocked) LevelUpGold else TextSecondary
                                            )
                                            Text(
                                                text = desc,
                                                style = Typography.bodyMedium.copy(fontSize = 12.sp),
                                                color = TextSecondary
                                            )
                                        }
                                    }

                                    if (profile?.title == titleName) {
                                        Text("EQUIPPED", style = Typography.labelSmall, color = NeonCyan)
                                    }
                                }
                            }
                        }
                    }

                    3 -> {
                        // 4. PRIVACY & LOCAL ARCHITECTURE & ADMIN TAB
                        item {
                            SystemSectionHeader(
                                title = "VERIFIED SYSTEM ADMIN AUTHORITY",
                                subtitle = "Shadow Monarch Creator Console",
                                icon = Icons.Default.WorkspacePremium
                            )
                        }

                        item {
                            GlassCard(
                                modifier = Modifier.fillMaxWidth(),
                                borderColor = LevelUpGold,
                                borderWidth = 1.5.dp,
                                backgroundColor = Color(0xFF1E1033)
                            ) {
                                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = "👑 ${profile?.hunterName ?: "Abhiron (Admin)"}",
                                                style = Typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                                color = LevelUpGold
                                            )
                                            Text(
                                                text = "Email: ${profile?.userEmail ?: "sabhiron5@gmail.com"}",
                                                style = Typography.labelSmall,
                                                color = Color(0xFFE8D0FF)
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(LevelUpGold)
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Text("GOD MODE", style = Typography.labelSmall.copy(fontWeight = FontWeight.Black, color = BackgroundVoid))
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text("• Account Tier: SYSTEM_ADMIN (Monarch VIP Unlimited)", style = Typography.bodyMedium, color = HealthGreen)
                                    Text("• Access Level: Unrestricted Root Privileges", style = Typography.bodyMedium, color = TextPrimary)
                                    Text("• Camera AI Bypass: Enabled (100% Verification Rate)", style = Typography.bodyMedium, color = NeonCyan)
                                    Text("• Unlimited Skill Tree Unlocks: Active", style = Typography.bodyMedium, color = LevelUpGold)

                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = { viewModel.adminInstantLevelUp(5) },
                                            colors = ButtonDefaults.buttonColors(containerColor = LevelUpGold, contentColor = BackgroundVoid),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text("+5 LEVELS", style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                                        }
                                        Button(
                                            onClick = { viewModel.setAdminAuthority("Abhiron (Admin)", "sabhiron5@gmail.com", true) },
                                            colors = ButtonDefaults.buttonColors(containerColor = MonarchPurple, contentColor = Color.White),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text("SYNC ADMIN", style = Typography.labelSmall)
                                        }
                                    }
                                }
                            }
                        }

                        item {
                            SystemSectionHeader(
                                title = "LOCAL-FIRST PRIVACY ENGINE",
                                subtitle = "Room SQLite Database • Zero tracking or ad profiling",
                                icon = Icons.Default.Security
                            )
                        }

                        item {
                            GlassCard(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Text("• Architecture: 100% Local-first with Room Persistence Library", style = Typography.bodyMedium, color = TextPrimary)
                                    Text("• Camera Proofs: Stored securely in private app cache", style = Typography.bodyMedium, color = TextPrimary)
                                    Text("• AI Processing: Gemini API evaluates real-time proof via HTTPS", style = Typography.bodyMedium, color = TextPrimary)
                                    Text("• Database Version: v1 (Active)", style = Typography.labelSmall, color = NeonCyan)
                                    Text("• System Mode: Offline-First Resilient", style = Typography.labelSmall, color = HealthGreen)
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
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "TIER ${skill.tier} • ${skill.name}",
                        style = Typography.titleMedium.copy(fontSize = 14.sp),
                        color = if (isUnlocked) HealthGreen else TextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = skill.description,
                    style = Typography.bodyMedium.copy(fontSize = 12.sp),
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "BUFF: ${skill.statBoostDescription}",
                    style = Typography.labelSmall,
                    color = NeonCyan
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            if (isUnlocked) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Unlocked",
                        tint = HealthGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("ACTIVE", style = Typography.labelSmall, color = HealthGreen)
                }
            } else {
                Button(
                    onClick = onUnlock,
                    enabled = canUnlock,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MonarchPurple,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("UNLOCK (${skill.costPoints} PTS)", style = Typography.labelSmall)
                }
            }
        }
    }
}
