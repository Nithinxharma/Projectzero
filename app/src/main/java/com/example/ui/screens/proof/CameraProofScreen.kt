package com.example.ui.screens.proof

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.ProofLogEntity
import com.example.data.local.entity.QuestEntity
import com.example.ui.components.CameraProofScanner
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ProofTypePreset(
    val type: String,
    val title: String,
    val icon: ImageVector,
    val defaultInstructions: String
)

@Composable
fun CameraProofScreen(
    viewModel: HunterViewModel,
    modifier: Modifier = Modifier
) {
    val allQuests by viewModel.allQuests.collectAsState()
    val proofLogs by viewModel.proofLogs.collectAsState()
    val isVerifying by viewModel.isVerifyingProof.collectAsState()

    val presets = listOf(
        ProofTypePreset("WATER", "Hydration Proof", Icons.Default.WaterDrop, "Show water bottle or filled glass"),
        ProofTypePreset("EXERCISE", "Workout Proof", Icons.Default.FitnessCenter, "Show gym setup, mat, or workout posture"),
        ProofTypePreset("READING", "Book Reading Proof", Icons.Default.Book, "Capture open book page clearly"),
        ProofTypePreset("STUDY", "Deep Study Proof", Icons.Default.School, "Show study desk, notes, or code screen"),
        ProofTypePreset("ROOM_CLEANING", "Clean Sanctuary", Icons.Default.CleaningServices, "Photograph clean workspace or room")
    )

    var selectedPreset by remember { mutableStateOf(presets[0]) }
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var selectedQuestForProof by remember { mutableStateOf<QuestEntity?>(null) }
    var currentTab by remember { mutableStateOf(0) } // 0 = Live Scanner, 1 = Verified History

    Box(modifier = modifier.fillMaxSize().background(BackgroundVoid)) {
        SystemAmbientParticles(particleCount = 20)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "AI VISION PROOF LAB",
                            style = Typography.headlineSmall,
                            color = NeonCyan
                        )
                        Text(
                            text = "Zero manual cheating. Prove daily execution.",
                            style = Typography.labelSmall,
                            color = TextSecondary
                        )
                    }

                    // Mode toggle (Scanner vs History)
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0x3300F0FF))
                            .padding(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (currentTab == 0) NeonCyan else Color.Transparent)
                                .clickable { currentTab = 0 }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "SCANNER",
                                style = Typography.labelSmall,
                                color = if (currentTab == 0) BackgroundVoid else TextSecondary
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (currentTab == 1) NeonCyan else Color.Transparent)
                                .clickable { currentTab = 1 }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "HISTORY",
                                style = Typography.labelSmall,
                                color = if (currentTab == 1) BackgroundVoid else TextSecondary
                            )
                        }
                    }
                }
            }

            if (currentTab == 0) {
                // Preset Selection Chips
                item {
                    Text(
                        text = "SELECT PROOF PROTOCOL",
                        style = Typography.labelMedium,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(presets) { preset ->
                            val isSelected = selectedPreset.type == preset.type
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        if (isSelected) MonarchPurple.copy(alpha = 0.4f)
                                        else Color(0x3312182B)
                                    )
                                    .border(
                                        1.dp,
                                        if (isSelected) NeonCyan else Color(0x3300F0FF),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable {
                                        selectedPreset = preset
                                        // Auto-match an uncompleted quest
                                        selectedQuestForProof = allQuests.firstOrNull {
                                            it.proofType.equals(preset.type, ignoreCase = true) && !it.isCompleted
                                        }
                                    }
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = preset.icon,
                                        contentDescription = null,
                                        tint = if (isSelected) NeonCyan else TextSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = preset.title,
                                        style = Typography.labelSmall,
                                        color = if (isSelected) TextPrimary else TextSecondary
                                    )
                                }
                            }
                        }
                    }
                }

                // Linked Quest Indicator
                item {
                    val matchingQuest = selectedQuestForProof ?: allQuests.firstOrNull {
                        it.proofType.equals(selectedPreset.type, ignoreCase = true) && !it.isCompleted
                    }

                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = if (matchingQuest != null) NeonCyan.copy(alpha = 0.5f) else LevelUpGold.copy(alpha = 0.4f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (matchingQuest != null) "TARGET QUEST: ${matchingQuest.title}" else "STANDALONE PROOF PROTOCOL",
                                    style = Typography.labelMedium,
                                    color = if (matchingQuest != null) NeonCyan else LevelUpGold
                                )
                                Text(
                                    text = selectedPreset.defaultInstructions,
                                    style = Typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = TextSecondary
                                )
                            }
                            if (matchingQuest != null) {
                                Text(
                                    text = "+${matchingQuest.xpReward} XP",
                                    style = Typography.labelSmall,
                                    color = LevelUpGold
                                )
                            }
                        }
                    }
                }

                // Camera Scanner Viewport
                item {
                    if (isVerifying) {
                        GlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(360.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
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
                                    text = "GEMINI AI VISION SCANNING EVIDENCE...",
                                    style = Typography.labelLarge,
                                    color = NeonCyan
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Validating task criteria for ${selectedPreset.title}",
                                    style = Typography.labelSmall,
                                    color = TextSecondary
                                )
                            }
                        }
                    } else {
                        CameraProofScanner(
                            taskTitle = selectedPreset.title,
                            proofType = selectedPreset.type,
                            instructions = selectedPreset.defaultInstructions,
                            onPhotoCaptured = { bitmap ->
                                capturedBitmap = bitmap
                                val targetQuest = selectedQuestForProof ?: allQuests.firstOrNull {
                                    it.proofType.equals(selectedPreset.type, ignoreCase = true) && !it.isCompleted
                                } ?: QuestEntity(
                                    title = selectedPreset.title,
                                    description = selectedPreset.defaultInstructions,
                                    category = "DAILY",
                                    statType = "DISCIPLINE",
                                    xpReward = 60,
                                    requiresCameraProof = true,
                                    proofType = selectedPreset.type,
                                    proofInstructions = selectedPreset.defaultInstructions
                                )
                                viewModel.completeQuestWithProof(targetQuest, bitmap)
                            }
                        )
                    }
                }

                // Visual Transformation (Posture / Physique / Room) Section
                item {
                    SystemSectionHeader(
                        title = "VISUAL TRANSFORMATION TRACKER",
                        subtitle = "AI detects posture, skin, cleanliness over time",
                        icon = Icons.Default.AutoAwesome
                    )

                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Take periodic photos of your posture, workspace, or physical conditioning. The AI compares your progression against past archives to detect subtle habit compounding.",
                                style = Typography.bodyMedium.copy(fontSize = 12.sp),
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { selectedPreset = presets[4] }, // Room
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0x3300F0FF),
                                        contentColor = NeonCyan
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("ROOM SCAN", style = Typography.labelSmall)
                                }
                                Button(
                                    onClick = { selectedPreset = presets[1] }, // Posture/Exercise
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0x339D4EDD),
                                        contentColor = Color(0xFFE8D0FF)
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("POSTURE SCAN", style = Typography.labelSmall)
                                }
                            }
                        }
                    }
                }
            } else {
                // HISTORY TAB: Past Proof Logs
                if (proofLogs.isEmpty()) {
                    item {
                        GlassCard(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = null,
                                    tint = TextSecondary,
                                    modifier = Modifier.size(40.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "NO PROOF LOGS RECORDED YET",
                                    style = Typography.titleMedium,
                                    color = TextSecondary
                                )
                                Text(
                                    text = "Capture photographic proofs to build your verified hunter log.",
                                    style = Typography.bodyMedium,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                } else {
                    items(proofLogs) { log ->
                        val sdf = SimpleDateFormat("MMM dd, yyyy • HH:mm", Locale.getDefault())
                        val dateString = sdf.format(Date(log.timestamp))

                        GlassCard(
                            modifier = Modifier.fillMaxWidth(),
                            borderColor = if (log.verificationStatus == "VERIFIED") HealthGreen.copy(alpha = 0.5f) else AlertCrimson.copy(alpha = 0.5f)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = log.questTitle,
                                        style = Typography.titleMedium.copy(fontSize = 14.sp),
                                        color = TextPrimary
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(
                                                if (log.verificationStatus == "VERIFIED") HealthGreen.copy(alpha = 0.2f)
                                                else AlertCrimson.copy(alpha = 0.2f)
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "${log.verificationStatus} (${log.confidenceScore}%)",
                                            style = Typography.labelSmall,
                                            color = if (log.verificationStatus == "VERIFIED") HealthGreen else AlertCrimson
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = dateString,
                                    style = Typography.labelSmall.copy(fontSize = 10.sp),
                                    color = TextSecondary
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = log.aiFeedback,
                                    style = Typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = Color(0xFFE2E8F0)
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Text(
                                        text = "AWARD: +${log.xpAwarded} XP",
                                        style = Typography.labelSmall,
                                        color = LevelUpGold
                                    )
                                    Text(
                                        text = "STAT: ${log.statAwarded}",
                                        style = Typography.labelSmall,
                                        color = NeonCyan
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
