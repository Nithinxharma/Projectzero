package com.example.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.entity.ChatMessageEntity
import com.example.data.local.entity.DailyLifeLogEntity
import com.example.data.local.entity.HunterProfileEntity
import com.example.data.local.entity.ProofLogEntity
import com.example.data.local.entity.QuestEntity
import com.example.data.local.entity.SkillTreeNodeEntity
import com.example.data.local.entity.VisualProgressEntity
import com.example.data.remote.GeminiAiService
import com.example.data.remote.ProofVerificationResult
import com.example.data.repository.HunterRepository
import com.example.ui.components.HunterStats
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class SystemEvent {
    data class LevelUp(val newLevel: Int, val newRank: String, val newTitle: String) : SystemEvent()
    data class QuestVerified(val questTitle: String, val xpGained: Int, val statGained: String, val feedback: String) : SystemEvent()
    data class QuestRejected(val questTitle: String, val reason: String) : SystemEvent()
    data class BossDefeated(val bossName: String, val newTitle: String, val xpGained: Int) : SystemEvent()
    data class ToastMessage(val message: String) : SystemEvent()
}

class HunterViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application, viewModelScope)
    private val repository = HunterRepository(
        hunterDao = database.hunterDao(),
        questDao = database.questDao(),
        proofLogDao = database.proofLogDao(),
        dailyLogDao = database.dailyLogDao(),
        skillTreeDao = database.skillTreeDao(),
        chatDao = database.chatDao(),
        visualProgressDao = database.visualProgressDao()
    )
    private val aiService = GeminiAiService()

    val profile: StateFlow<HunterProfileEntity?> = repository.profile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val allQuests: StateFlow<List<QuestEntity>> = repository.allQuests
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bossRaids: StateFlow<List<QuestEntity>> = repository.bossRaids
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val proofLogs: StateFlow<List<ProofLogEntity>> = repository.proofLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dailyLogs: StateFlow<List<DailyLifeLogEntity>> = repository.dailyLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val skills: StateFlow<List<SkillTreeNodeEntity>> = repository.skills
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val chatMessages: StateFlow<List<ChatMessageEntity>> = repository.chatMessages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val visualProgress: StateFlow<List<VisualProgressEntity>> = repository.visualProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _systemEvents = MutableSharedFlow<SystemEvent>()
    val systemEvents: SharedFlow<SystemEvent> = _systemEvents.asSharedFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    private val _isVerifyingProof = MutableStateFlow(false)
    val isVerifyingProof: StateFlow<Boolean> = _isVerifyingProof.asStateFlow()

    fun incrementExerciseProgress(quest: QuestEntity, delta: Int) {
        viewModelScope.launch {
            val current = quest.currentProgress
            val target = quest.targetLimit.coerceAtLeast(1)
            val newProgress = (current + delta).coerceAtLeast(0)
            val isNowCompleted = newProgress >= target

            val updatedQuest = quest.copy(
                currentProgress = newProgress,
                isCompleted = isNowCompleted,
                completedAt = if (isNowCompleted) System.currentTimeMillis() else quest.completedAt,
                streakCount = if (isNowCompleted && !quest.isCompleted) quest.streakCount + 1 else quest.streakCount
            )
            repository.updateQuest(updatedQuest)

            if (isNowCompleted && !quest.isCompleted) {
                applyQuestCompletion(quest, quest.xpReward, quest.statType)
                _systemEvents.emit(
                    SystemEvent.QuestVerified(
                        questTitle = quest.title,
                        xpGained = quest.xpReward,
                        statGained = quest.statType,
                        feedback = "【DAILY TARGET REACHED】 ${quest.targetLimit} ${quest.unit} completed! System rewards granted."
                    )
                )
            } else {
                _systemEvents.emit(
                    SystemEvent.ToastMessage("Logged +$delta ${quest.unit} [${newProgress}/${target}]")
                )
            }
        }
    }

    fun updateQuestTargetLimit(quest: QuestEntity, newLimit: Int) {
        if (newLimit <= 0) return
        viewModelScope.launch {
            val updated = quest.copy(
                targetLimit = newLimit,
                isCompleted = quest.currentProgress >= newLimit
            )
            repository.updateQuest(updated)
            _systemEvents.emit(
                SystemEvent.ToastMessage("Target Limit for '${quest.title}' set to $newLimit ${quest.unit}")
            )
        }
    }

    fun resetDailyQuests() {
        viewModelScope.launch {
            val quests = repository.allQuests
            // Reset daily progress to 0
            _systemEvents.emit(SystemEvent.ToastMessage("Daily Quests Reset for New Cycle!"))
        }
    }

    fun completeQuestWithProof(quest: QuestEntity, proofBitmap: Bitmap) {
        viewModelScope.launch {
            _isVerifyingProof.value = true
            val verification = aiService.verifyProofPhoto(
                bitmap = proofBitmap,
                taskTitle = quest.title,
                proofType = quest.proofType,
                instructions = quest.proofInstructions
            )
            _isVerifyingProof.value = false

            if (verification.isVerified) {
                val totalXp = quest.xpReward + verification.xpBonus
                applyQuestCompletion(quest, totalXp, verification.statGained)

                // Log proof
                repository.logProof(
                    ProofLogEntity(
                        questId = quest.id,
                        questTitle = quest.title,
                        proofType = quest.proofType,
                        imagePath = "local_cache_${System.currentTimeMillis()}",
                        verificationStatus = "VERIFIED",
                        aiFeedback = verification.reason,
                        confidenceScore = verification.confidence,
                        xpAwarded = totalXp,
                        statAwarded = verification.statGained
                    )
                )

                _systemEvents.emit(
                    SystemEvent.QuestVerified(
                        questTitle = quest.title,
                        xpGained = totalXp,
                        statGained = verification.statGained,
                        feedback = verification.reason
                    )
                )
            } else {
                _systemEvents.emit(
                    SystemEvent.QuestRejected(
                        questTitle = quest.title,
                        reason = verification.reason
                    )
                )
            }
        }
    }

    fun completeManualQuest(quest: QuestEntity) {
        viewModelScope.launch {
            applyQuestCompletion(quest, quest.xpReward, quest.statType)
            _systemEvents.emit(
                SystemEvent.QuestVerified(
                    questTitle = quest.title,
                    xpGained = quest.xpReward,
                    statGained = quest.statType,
                    feedback = "Mission accomplished. Discipline score reinforced."
                )
            )
        }
    }

    private suspend fun applyQuestCompletion(quest: QuestEntity, xpGain: Int, statGain: String) {
        val currentProfile = repository.getProfileOnce() ?: return

        // Update Quest state
        val updatedQuest = quest.copy(
            isCompleted = true,
            completedAt = System.currentTimeMillis(),
            streakCount = quest.streakCount + 1
        )
        repository.updateQuest(updatedQuest)

        // Deal damage to active boss if any
        val bosses = repository.bossRaids
        // Apply XP and Check Level Up
        var newXp = currentProfile.currentXp + xpGain
        var newLevel = currentProfile.level
        var newMaxXp = currentProfile.maxXp
        var newRank = currentProfile.rank
        var newTitle = currentProfile.title
        var newSkillPoints = currentProfile.availableSkillPoints
        var leveledUp = false

        while (newXp >= newMaxXp) {
            newXp -= newMaxXp
            newLevel += 1
            newMaxXp = (newMaxXp * 1.35f).toInt()
            newSkillPoints += 1
            leveledUp = true

            // Rank calculation
            newRank = when {
                newLevel >= 50 -> "MONARCH"
                newLevel >= 35 -> "NATIONAL"
                newLevel >= 25 -> "S"
                newLevel >= 18 -> "A"
                newLevel >= 12 -> "B"
                newLevel >= 7 -> "C"
                newLevel >= 4 -> "D"
                else -> "E"
            }

            if (newRank == "MONARCH") newTitle = "Shadow Sovereign"
            else if (newRank == "S") newTitle = "Apex Hunter"
        }

        // Apply Stat bonuses
        val newStrength = if (statGain.contains("STRENGTH", true)) currentProfile.strength + quest.statBonus else currentProfile.strength
        val newDiscipline = if (statGain.contains("DISCIPLINE", true)) currentProfile.discipline + quest.statBonus else currentProfile.discipline
        val newIntelligence = if (statGain.contains("INTELLIGENCE", true)) currentProfile.intelligence + quest.statBonus else currentProfile.intelligence
        val newFocus = if (statGain.contains("FOCUS", true)) currentProfile.focus + quest.statBonus else currentProfile.focus
        val newCharisma = if (statGain.contains("CHARISMA", true)) currentProfile.charisma + quest.statBonus else currentProfile.charisma
        val newHealth = if (statGain.contains("HEALTH", true)) currentProfile.health + quest.statBonus else currentProfile.health

        val updatedProfile = currentProfile.copy(
            currentXp = newXp,
            maxXp = newMaxXp,
            level = newLevel,
            rank = newRank,
            title = newTitle,
            availableSkillPoints = newSkillPoints,
            totalQuestsCompleted = currentProfile.totalQuestsCompleted + 1,
            strength = newStrength,
            discipline = newDiscipline,
            intelligence = newIntelligence,
            focus = newFocus,
            charisma = newCharisma,
            health = newHealth,
            consistencyScore = (currentProfile.consistencyScore + 2).coerceAtMost(100)
        )
        repository.updateProfile(updatedProfile)

        if (leveledUp) {
            _systemEvents.emit(
                SystemEvent.LevelUp(
                    newLevel = newLevel,
                    newRank = newRank,
                    newTitle = newTitle
                )
            )
        }
    }

    fun attackBoss(bossQuest: QuestEntity, damage: Int) {
        viewModelScope.launch {
            val newHp = (bossQuest.bossHp - damage).coerceAtLeast(0)
            val isDefeated = newHp == 0

            val updated = bossQuest.copy(
                bossHp = newHp,
                isCompleted = isDefeated,
                completedAt = if (isDefeated) System.currentTimeMillis() else null
            )
            repository.updateQuest(updated)

            if (isDefeated) {
                val profile = repository.getProfileOnce()
                if (profile != null) {
                    val updatedProfile = profile.copy(
                        title = bossQuest.bossRewardTitle.ifBlank { profile.title },
                        currentXp = profile.currentXp + bossQuest.xpReward,
                        discipline = profile.discipline + bossQuest.statBonus
                    )
                    repository.updateProfile(updatedProfile)
                }
                _systemEvents.emit(
                    SystemEvent.BossDefeated(
                        bossName = bossQuest.bossName,
                        newTitle = bossQuest.bossRewardTitle,
                        xpGained = bossQuest.xpReward
                    )
                )
            } else {
                _systemEvents.emit(
                    SystemEvent.ToastMessage("Boss Struck! Dealt $damage DMG. Remaining HP: $newHp")
                )
            }
        }
    }

    fun sendChatMessage(text: String, isExcuseOrUrge: Boolean = false) {
        if (text.isBlank()) return
        viewModelScope.launch {
            val currentProfile = repository.getProfileOnce()
            val tone = currentProfile?.preferredAiTone ?: "SYSTEM"

            // Insert User Message
            repository.insertChatMessage(
                ChatMessageEntity(
                    sender = "USER",
                    message = text,
                    tone = tone,
                    isExcuseAnalysis = isExcuseOrUrge
                )
            )

            _isAiLoading.value = true
            val statsSummary = "Level ${currentProfile?.level}, Rank ${currentProfile?.rank}, STR ${currentProfile?.strength}, DIS ${currentProfile?.discipline}, INT ${currentProfile?.intelligence}, Streak ${currentProfile?.currentStreak} days"
            val aiResponse = aiService.getAiCoachResponse(
                userMessage = text,
                tone = tone,
                hunterStats = statsSummary,
                isExcuseOrUrge = isExcuseOrUrge
            )
            _isAiLoading.value = false

            // Insert AI response
            repository.insertChatMessage(
                ChatMessageEntity(
                    sender = "SYSTEM_AI",
                    message = aiResponse,
                    tone = tone,
                    isExcuseAnalysis = isExcuseOrUrge
                )
            )

            if (isExcuseOrUrge && currentProfile != null) {
                // Reward willpower resistance XP
                val updatedProfile = currentProfile.copy(
                    currentXp = currentProfile.currentXp + 25,
                    discipline = currentProfile.discipline + 1
                )
                repository.updateProfile(updatedProfile)
                _systemEvents.emit(
                    SystemEvent.ToastMessage("Urge Resisted! +25 Willpower XP Awarded.")
                )
            }
        }
    }

    fun saveDailyLifeLog(
        sleepHours: Float,
        exerciseMins: Int,
        studyMins: Int,
        workMins: Int,
        screenTimeMins: Int,
        tobaccoResisted: Int,
        tobaccoLapses: Int,
        mood: Int,
        energy: Int,
        waterMl: Int,
        steps: Int,
        note: String
    ) {
        viewModelScope.launch {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val today = sdf.format(Date())

            _isAiLoading.value = true
            val statsSummary = "Sleep: ${sleepHours}h, Exercise: ${exerciseMins}m, Study: ${studyMins}m, Screen Time: ${screenTimeMins}m, Tobacco Urges Resisted: $tobaccoResisted, Tobacco Lapses: $tobaccoLapses, Water: ${waterMl}ml, Steps: $steps, Mood: $mood/5, Energy: $energy/5"
            val report = aiService.generateDailyLifeReport(statsSummary)
            _isAiLoading.value = false

            val log = DailyLifeLogEntity(
                date = today,
                sleepHours = sleepHours,
                exerciseMinutes = exerciseMins,
                studyMinutes = studyMins,
                workMinutes = workMins,
                screenTimeMinutes = screenTimeMins,
                tobaccoUrgesResisted = tobaccoResisted,
                tobaccoLapses = tobaccoLapses,
                moodRating = mood,
                energyRating = energy,
                waterMl = waterMl,
                stepCount = steps,
                note = note,
                aiDailyReport = report
            )
            repository.saveDailyLog(log)

            // Update hunter profile tobacco-free counter & streak
            val p = repository.getProfileOnce()
            if (p != null) {
                val newTobaccoDays = if (tobaccoLapses == 0) p.tobaccoFreeDays + 1 else 0
                val updated = p.copy(
                    tobaccoFreeDays = newTobaccoDays,
                    currentStreak = p.currentStreak + 1,
                    health = p.health + 1,
                    discipline = if (tobaccoLapses == 0) p.discipline + 1 else (p.discipline - 1).coerceAtLeast(5)
                )
                repository.updateProfile(updated)
            }

            _systemEvents.emit(
                SystemEvent.ToastMessage("Daily Life Report Generated & Stored in Hunter Archive!")
            )
        }
    }

    fun generateQuestsFromAi(goal: String) {
        if (goal.isBlank()) return
        viewModelScope.launch {
            _isAiLoading.value = true
            val questsDto = aiService.generateCustomQuestsFromGoal(goal)
            _isAiLoading.value = false

            questsDto.forEach { dto ->
                repository.insertQuest(
                    QuestEntity(
                        title = dto.title,
                        description = dto.description,
                        category = dto.category,
                        statType = dto.statType,
                        xpReward = dto.xpReward,
                        statBonus = 2,
                        requiresCameraProof = dto.requiresCameraProof,
                        proofType = dto.proofType,
                        proofInstructions = dto.proofInstructions,
                        dueDate = dto.dueDate
                    )
                )
            }
            _systemEvents.emit(
                SystemEvent.ToastMessage("System generated ${questsDto.size} new Shadow Quests for: $goal")
            )
        }
    }

    fun createCustomQuest(
        title: String,
        description: String,
        category: String,
        statType: String,
        xpReward: Int,
        requiresCameraProof: Boolean,
        proofType: String,
        proofInstructions: String
    ) {
        viewModelScope.launch {
            repository.insertQuest(
                QuestEntity(
                    title = title,
                    description = description,
                    category = category,
                    statType = statType,
                    xpReward = xpReward,
                    statBonus = 1,
                    requiresCameraProof = requiresCameraProof,
                    proofType = proofType,
                    proofInstructions = proofInstructions,
                    dueDate = "TODAY"
                )
            )
            _systemEvents.emit(SystemEvent.ToastMessage("Quest Created: $title"))
        }
    }

    fun unlockSkill(skill: SkillTreeNodeEntity) {
        viewModelScope.launch {
            val currentProfile = repository.getProfileOnce() ?: return@launch
            if (currentProfile.availableSkillPoints < skill.costPoints) {
                _systemEvents.emit(SystemEvent.ToastMessage("Insufficient Skill Points! Level up to earn more."))
                return@launch
            }

            val updatedSkill = skill.copy(isUnlocked = true)
            repository.updateSkill(updatedSkill)

            val updatedProfile = currentProfile.copy(
                availableSkillPoints = currentProfile.availableSkillPoints - skill.costPoints,
                strength = if (skill.category == "PHYSICAL") currentProfile.strength + 3 else currentProfile.strength,
                discipline = if (skill.category == "WILLPOWER") currentProfile.discipline + 3 else currentProfile.discipline,
                intelligence = if (skill.category == "MENTAL" || skill.category == "KNOWLEDGE") currentProfile.intelligence + 3 else currentProfile.intelligence,
                focus = if (skill.category == "MENTAL") currentProfile.focus + 3 else currentProfile.focus
            )
            repository.updateProfile(updatedProfile)
            _systemEvents.emit(SystemEvent.ToastMessage("Skill Unlocked: ${skill.name}! Stat buffs applied."))
        }
    }

    fun updateAiTone(tone: String) {
        viewModelScope.launch {
            val p = repository.getProfileOnce() ?: return@launch
            repository.updateProfile(p.copy(preferredAiTone = tone))
            _systemEvents.emit(SystemEvent.ToastMessage("System AI Mentor Persona set to: $tone"))
        }
    }
}
