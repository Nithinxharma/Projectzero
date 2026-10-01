package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.HunterProfileEntity
import com.example.ui.theme.AlertCrimson
import com.example.ui.theme.BackgroundSurface
import com.example.ui.theme.BackgroundVoid
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.LevelUpGold
import com.example.ui.theme.MonarchPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ShadowIndigo
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.Typography

@Composable
fun AdminCommandCenterModal(
    profile: HunterProfileEntity?,
    onDismiss: () -> Unit,
    onActivateAdmin: (String, String) -> Unit,
    onInstantLevelUp: (Int) -> Unit,
    onResetToDayZero: () -> Unit = {}
) {
    var userName by remember { mutableStateOf(profile?.hunterName ?: "Abhiron") }
    var userEmail by remember { mutableStateOf(profile?.userEmail ?: "sabhiron5@gmail.com") }
    var userHeadline by remember { mutableStateOf(profile?.title ?: "High-Performance Practitioner") }
    var selectedTone by remember { mutableStateOf(profile?.preferredAiTone ?: "PERFORMANCE") }
    var showDevTools by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("admin_command_center_card"),
            borderColor = ShadowIndigo.copy(alpha = 0.6f),
            borderWidth = 1.5.dp,
            backgroundColor = Color(0xFF141926)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(ShadowIndigo.copy(alpha = 0.2f))
                                .border(1.dp, ShadowIndigo, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ManageAccounts,
                                contentDescription = null,
                                tint = NeonCyan,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "PERSONAL PROFILE & ACCOUNT",
                                style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                            Text(
                                text = "Configure your identity, goals, and system settings",
                                style = Typography.labelSmall,
                                color = TextSecondary
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Text("✕", color = Color.White, fontSize = 20.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Identity Fields
                Text(
                    text = "YOUR IDENTITY",
                    style = Typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = NeonCyan
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Full Name / Display Name") },
                    placeholder = { Text("e.g. Abhiron, Alex, Sarah") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = GlassBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = userEmail,
                    onValueChange = { userEmail = it },
                    label = { Text("Account Email") },
                    placeholder = { Text("your.email@example.com") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = GlassBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = userHeadline,
                    onValueChange = { userHeadline = it },
                    label = { Text("Profession / Personal Focus") },
                    placeholder = { Text("e.g. Software Engineer, Runner, Founder") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = GlassBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // AI Coach Style Selector
                Text(
                    text = "AI COACHING STYLE",
                    style = Typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = ShadowIndigo
                )

                Spacer(modifier = Modifier.height(8.dp))

                val tones = listOf(
                    "PERFORMANCE" to "Performance Coach",
                    "SCIENTIFIC" to "Neuroscience & Habit Logic",
                    "EMPATHETIC" to "Supportive & Mindful"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tones.forEach { (key, label) ->
                        val isSelected = selectedTone == key
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) ShadowIndigo else BackgroundSurface)
                                .border(1.dp, if (isSelected) NeonCyan else GlassBorder, RoundedCornerShape(8.dp))
                                .clickable { selectedTone = key }
                                .padding(vertical = 10.dp, horizontal = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                style = Typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Color.White else TextSecondary
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Save Profile Button
                Button(
                    onClick = {
                        onActivateAdmin(userName, userEmail)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonCyan,
                        contentColor = BackgroundVoid
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "SAVE PROFILE CHANGES",
                        style = Typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Reset to Day 0 (Fresh Start) Button
                Button(
                    onClick = {
                        onResetToDayZero()
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E2638),
                        contentColor = Color(0xFFEF4444)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0x66EF4444), RoundedCornerShape(10.dp))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.RestartAlt,
                            contentDescription = null,
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "RESET TO DAY 0 (START FROM ZERO)",
                            style = Typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFFEF4444))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Master / Dev Tools Expander
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDevTools = !showDevTools }
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = null,
                            tint = LevelUpGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "DEVELOPER & MASTER TEST CONTROLS",
                            style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = LevelUpGold
                        )
                    }
                    Text(
                        text = if (showDevTools) "▲ Hide" else "▼ Show",
                        style = Typography.labelSmall,
                        color = TextMuted
                    )
                }

                if (showDevTools) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0F1420))
                            .border(1.dp, LevelUpGold.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "Quick Simulation & Progression Testing",
                                style = Typography.labelSmall,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { onInstantLevelUp(1) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = ShadowIndigo,
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("+1 Day Streak", style = Typography.labelSmall)
                                }

                                Button(
                                    onClick = { onInstantLevelUp(7) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = LevelUpGold,
                                        contentColor = BackgroundVoid
                                    ),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("+7 Days Boost", style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
