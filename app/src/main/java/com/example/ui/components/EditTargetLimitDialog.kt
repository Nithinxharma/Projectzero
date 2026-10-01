package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.data.local.entity.QuestEntity
import com.example.ui.theme.BackgroundVoid
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.LevelUpGold
import com.example.ui.theme.MonarchPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.Typography

@Composable
fun EditTargetLimitDialog(
    quest: QuestEntity,
    onDismiss: () -> Unit,
    onSaveLimit: (Int) -> Unit
) {
    var limitInput by remember { mutableStateOf(quest.targetLimit.toString()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            borderColor = NeonCyan,
            backgroundColor = Color(0xFF101428)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CUSTOMIZE DAILY LIMIT",
                        style = Typography.titleMedium,
                        color = NeonCyan
                    )
                    IconButton(onClick = onDismiss) {
                        Text("✕", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Configure target quota for: ${quest.title}",
                    style = Typography.bodyMedium,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Quick Preset Chips
                val presets = when (quest.unit) {
                    "km" -> listOf(3, 5, 10, 15)
                    "ml" -> listOf(1500, 2500, 3000, 4000)
                    "mins" -> listOf(30, 45, 60, 90)
                    else -> listOf(20, 50, 100, 150, 200)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    presets.forEach { preset ->
                        Button(
                            onClick = { limitInput = preset.toString() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (limitInput == preset.toString()) NeonCyan else Color(0x3300F0FF),
                                contentColor = if (limitInput == preset.toString()) BackgroundVoid else TextPrimary
                            ),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("$preset", style = Typography.labelSmall)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = limitInput,
                    onValueChange = { limitInput = it },
                    label = { Text("Target Goal (${quest.unit})") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = GlassBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val num = limitInput.toIntOrNull() ?: quest.targetLimit
                        onSaveLimit(num)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonCyan,
                        contentColor = BackgroundVoid
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("SAVE DAILY TARGET", style = Typography.labelLarge)
                }
            }
        }
    }
}
