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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.WorkspacePremium
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
import com.example.ui.theme.BackgroundVoid
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.LevelUpGold
import com.example.ui.theme.MonarchPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextCyan
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.Typography

@Composable
fun AdminCommandCenterModal(
    profile: HunterProfileEntity?,
    onDismiss: () -> Unit,
    onActivateAdmin: (String, String) -> Unit,
    onInstantLevelUp: (Int) -> Unit
) {
    var adminName by remember { mutableStateOf(profile?.hunterName ?: "Abhiron (Admin)") }
    var adminEmail by remember { mutableStateOf(profile?.userEmail ?: "sabhiron5@gmail.com") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.92f))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("admin_command_center_card"),
            borderColor = LevelUpGold,
            borderWidth = 2.dp,
            backgroundColor = Color(0xFF140D26)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(LevelUpGold.copy(alpha = 0.2f))
                                .border(1.dp, LevelUpGold, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AdminPanelSettings,
                                contentDescription = null,
                                tint = LevelUpGold,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "SYSTEM ADMIN COMMAND CENTER",
                                style = Typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                color = LevelUpGold
                            )
                            Text(
                                text = "Shadow Monarch Authority • Unrestricted VIP Access",
                                style = Typography.labelSmall,
                                color = Color(0xFFE8D0FF)
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Text("✕", color = Color.White, fontSize = 20.sp)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Verified Status Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF3B007A), Color(0xFF700021))
                            )
                        )
                        .border(1.5.dp, LevelUpGold, RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "ACCOUNT AUTHORITY: SYSTEM ADMIN",
                                style = Typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = LevelUpGold
                            )
                            Text(
                                text = "User: $adminEmail",
                                style = Typography.labelSmall,
                                color = TextPrimary
                            )
                            Text(
                                text = "Status: Verified System Creator (God Mode Active)",
                                style = Typography.labelSmall.copy(fontSize = 10.sp),
                                color = HealthGreen
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(LevelUpGold)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "👑 ADMIN",
                                style = Typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    color = BackgroundVoid
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "GOD MODE CHEAT CODES & SYSTEM OVERRIDES",
                    style = Typography.labelMedium,
                    color = NeonCyan
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Quick Override Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onInstantLevelUp(1) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MonarchPurple,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("+1 LEVEL", style = Typography.labelSmall)
                    }

                    Button(
                        onClick = { onInstantLevelUp(5) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LevelUpGold,
                            contentColor = BackgroundVoid
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("+5 ASCEND", style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    }

                    Button(
                        onClick = { onInstantLevelUp(20) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonCyan,
                            contentColor = BackgroundVoid
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("MONARCH MAX", style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Identity Configuration
                OutlinedTextField(
                    value = adminName,
                    onValueChange = { adminName = it },
                    label = { Text("Hunter Identity Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LevelUpGold,
                        unfocusedBorderColor = GlassBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = adminEmail,
                    onValueChange = { adminEmail = it },
                    label = { Text("Admin Account Email") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LevelUpGold,
                        unfocusedBorderColor = GlassBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onActivateAdmin(adminName, adminEmail)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LevelUpGold,
                        contentColor = BackgroundVoid
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("APPLY SYSTEM ADMIN PRIVILEGES", style = Typography.labelLarge)
                }
            }
        }
    }
}
