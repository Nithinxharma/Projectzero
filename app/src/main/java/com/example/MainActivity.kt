package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.GlassCard
import com.example.ui.components.RankBadge
import com.example.ui.components.SystemAmbientParticles
import com.example.ui.screens.aicoach.AiCoachScreen
import com.example.ui.screens.arsenal.SkillTreeScreen
import com.example.ui.screens.hud.HunterHudScreen
import com.example.ui.screens.proof.CameraProofScreen
import com.example.ui.screens.quests.QuestsScreen
import com.example.ui.theme.AlertCrimson
import com.example.ui.theme.BackgroundSurface
import com.example.ui.theme.BackgroundVoid
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.LevelUpGold
import com.example.ui.theme.MonarchPurple
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextCyan
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.Typography
import com.example.ui.viewmodel.HunterViewModel
import com.example.ui.viewmodel.SystemEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

enum class Screen(val title: String, val icon: ImageVector, val tag: String) {
    HUD("HUD", Icons.Default.Dashboard, "nav_hud"),
    QUESTS("QUESTS", Icons.Default.FormatListBulleted, "nav_quests"),
    PROOF("PROOF", Icons.Default.CameraAlt, "nav_proof"),
    COACH("AI COACH", Icons.Default.Psychology, "nav_coach"),
    ARSENAL("ARSENAL", Icons.Default.Shield, "nav_arsenal")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ProjectZeroApp()
            }
        }
    }
}

@Composable
fun ProjectZeroApp() {
    val context = LocalContext.current
    val viewModel: HunterViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var currentScreen by remember { mutableStateOf(Screen.HUD) }
    var levelUpDialogEvent by remember { mutableStateOf<SystemEvent.LevelUp?>(null) }
    var bossDefeatedEvent by remember { mutableStateOf<SystemEvent.BossDefeated?>(null) }

    // Back button handling
    if (currentScreen != Screen.HUD) {
        BackHandler {
            currentScreen = Screen.HUD
        }
    }

    // System Events Collector
    LaunchedEffect(Unit) {
        viewModel.systemEvents.collectLatest { event ->
            when (event) {
                is SystemEvent.LevelUp -> {
                    levelUpDialogEvent = event
                }
                is SystemEvent.BossDefeated -> {
                    bossDefeatedEvent = event
                }
                is SystemEvent.QuestVerified -> {
                    scope.launch {
                        snackbarHostState.showSnackbar("【TASK VERIFIED】 +${event.xpGained} XP • ${event.statGained} stat buff!")
                    }
                }
                is SystemEvent.QuestRejected -> {
                    scope.launch {
                        snackbarHostState.showSnackbar("【TASK REJECTED】 ${event.reason}")
                    }
                }
                is SystemEvent.ToastMessage -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundVoid),
        containerColor = BackgroundVoid,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .padding(bottom = 76.dp)
                    .windowInsetsPadding(WindowInsets.navigationBars)
            )
        },
        bottomBar = {
            GlassBottomNavigationDock(
                currentScreen = currentScreen,
                onScreenSelected = { currentScreen = it }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            when (currentScreen) {
                Screen.HUD -> HunterHudScreen(
                    viewModel = viewModel,
                    onNavigateToQuests = { currentScreen = Screen.QUESTS },
                    onNavigateToCoach = { currentScreen = Screen.COACH },
                    onNavigateToProof = { currentScreen = Screen.PROOF }
                )
                Screen.QUESTS -> QuestsScreen(viewModel = viewModel)
                Screen.PROOF -> CameraProofScreen(viewModel = viewModel)
                Screen.COACH -> AiCoachScreen(viewModel = viewModel)
                Screen.ARSENAL -> SkillTreeScreen(viewModel = viewModel)
            }

            // LEVEL UP CELEBRATION MODAL
            levelUpDialogEvent?.let { evt ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.92f))
                        .clickable { levelUpDialogEvent = null }
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SystemAmbientParticles(particleCount = 50)

                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("level_up_dialog"),
                        borderColor = LevelUpGold,
                        borderWidth = 2.dp,
                        backgroundColor = Color(0xFF140F2E)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(LevelUpGold.copy(alpha = 0.2f))
                                    .border(2.dp, LevelUpGold, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.WorkspacePremium,
                                    contentDescription = null,
                                    tint = LevelUpGold,
                                    modifier = Modifier.size(44.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "【 SYSTEM LEVEL UP 】",
                                style = Typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                                color = LevelUpGold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "YOU HAVE ASCENDED TO LEVEL ${evt.newLevel}",
                                style = Typography.titleLarge,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            RankBadge(rank = evt.newRank, showTitle = true)

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Attributes enhanced! +1 Skill Point awarded to invest in the Skill Tree.",
                                style = Typography.bodyMedium,
                                color = Color(0xFFE2E8F0)
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = { levelUpDialogEvent = null },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = LevelUpGold,
                                    contentColor = BackgroundVoid
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth().testTag("level_up_dismiss_btn")
                            ) {
                                Text("CLAIM ATTRIBUTE BOOSTS", style = Typography.labelLarge)
                            }
                        }
                    }
                }
            }

            // BOSS DEFEATED VICTORY MODAL
            bossDefeatedEvent?.let { bossEvt ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.92f))
                        .clickable { bossDefeatedEvent = null }
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SystemAmbientParticles(particleCount = 50)

                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = AlertCrimson,
                        borderWidth = 2.dp,
                        backgroundColor = Color(0xFF220815)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = LevelUpGold,
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "BOSS CONQUERED!",
                                style = Typography.headlineMedium,
                                color = AlertCrimson
                            )
                            Text(
                                text = bossEvt.bossName.uppercase(),
                                style = Typography.titleMedium,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "NEW TITLE UNLOCKED: \"${bossEvt.newTitle}\"",
                                style = Typography.labelLarge,
                                color = LevelUpGold
                            )
                            Text(
                                text = "+${bossEvt.xpGained} XP Awarded",
                                style = Typography.labelSmall,
                                color = NeonCyan
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(
                                onClick = { bossDefeatedEvent = null },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AlertCrimson,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("CLAIM VICTORY", style = Typography.labelLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GlassBottomNavigationDock(
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            borderColor = NeonCyan.copy(alpha = 0.35f),
            backgroundColor = Color(0xEE0B0E1B),
            elevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Screen.values().forEach { screen ->
                    val isSelected = currentScreen == screen
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (isSelected) MonarchPurple.copy(alpha = 0.35f)
                                else Color.Transparent
                            )
                            .border(
                                width = if (isSelected) 1.dp else 0.dp,
                                color = if (isSelected) NeonCyan else Color.Transparent,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { onScreenSelected(screen) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                            .testTag(screen.tag),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title,
                                tint = if (isSelected) NeonCyan else TextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = screen.title,
                                style = Typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = if (isSelected) NeonCyan else TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}
