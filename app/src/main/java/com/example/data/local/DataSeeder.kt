package com.example.data.local

import com.example.data.local.entity.ChatMessageEntity
import com.example.data.local.entity.DailyLifeLogEntity
import com.example.data.local.entity.HunterProfileEntity
import com.example.data.local.entity.QuestEntity
import com.example.data.local.entity.SkillTreeNodeEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DataSeeder {
    fun getInitialProfile(): HunterProfileEntity {
        return HunterProfileEntity(
            id = 1,
            hunterName = "Abhiron",
            userEmail = "sabhiron5@gmail.com",
            title = "High-Performance Practitioner",
            level = 0,
            currentXp = 0,
            maxXp = 100,
            rank = "INITIATE",
            accountTier = "PRACTITIONER",
            isAdmin = true,
            godModeEnabled = false,
            hp = 100,
            maxHp = 100,
            mp = 100,
            maxMp = 100,
            strength = 10,
            discipline = 10,
            intelligence = 10,
            focus = 10,
            charisma = 10,
            health = 10,
            availableSkillPoints = 0,
            totalQuestsCompleted = 0,
            currentStreak = 0,
            highestStreak = 0,
            consistencyScore = 0,
            burnoutRisk = "Low",
            tobaccoFreeDays = 0,
            soundEnabled = true,
            hapticsEnabled = true,
            preferredAiTone = "PERFORMANCE"
        )
    }

    fun getInitialQuests(): List<QuestEntity> {
        return listOf(
            // 1. Daily Physical Regimen (Starts at 0)
            QuestEntity(
                title = "Daily Push-ups (50 Reps)",
                description = "Upper body strength, shoulder stability, and core engagement.",
                category = "DAILY",
                statType = "STRENGTH",
                xpReward = 50,
                statBonus = 2,
                requiresCameraProof = true,
                proofType = "EXERCISE",
                proofInstructions = "Photo of workout space, pushup form, or training gear",
                dueDate = "BEFORE MIDNIGHT",
                isCountable = true,
                currentProgress = 0,
                targetLimit = 50,
                unit = "reps",
                isCompleted = false,
                streakCount = 0
            ),
            QuestEntity(
                title = "Bodyweight Squats (75 Reps)",
                description = "Lower body endurance, hip mobility, and cardiovascular stimulus.",
                category = "DAILY",
                statType = "HEALTH",
                xpReward = 60,
                statBonus = 2,
                requiresCameraProof = false,
                dueDate = "BEFORE MIDNIGHT",
                isCountable = true,
                currentProgress = 0,
                targetLimit = 75,
                unit = "reps",
                isCompleted = false,
                streakCount = 0
            ),
            QuestEntity(
                title = "Core Planks & Ab Crunches (50 Reps/Sec)",
                description = "Spinal stability, abdominal strength, and posture correction.",
                category = "DAILY",
                statType = "STRENGTH",
                xpReward = 50,
                statBonus = 2,
                requiresCameraProof = false,
                dueDate = "BEFORE MIDNIGHT",
                isCountable = true,
                currentProgress = 0,
                targetLimit = 50,
                unit = "reps",
                isCompleted = false,
                streakCount = 0
            ),
            QuestEntity(
                title = "Cardio Run / 8,000 Brisk Steps",
                description = "Aerobic fitness, heart health, and natural endorphin elevation.",
                category = "DAILY",
                statType = "HEALTH",
                xpReward = 80,
                statBonus = 3,
                requiresCameraProof = true,
                proofType = "WALKING",
                proofInstructions = "Screenshot of fitness app step counter or outdoor route",
                dueDate = "BEFORE MIDNIGHT",
                isCountable = true,
                currentProgress = 0,
                targetLimit = 5,
                unit = "km",
                isCompleted = false,
                streakCount = 0
            ),

            // 2. Cognitive & Deep Work Habits (Starts at 0)
            QuestEntity(
                title = "Deep Work Block (60 Mins)",
                description = "Uninterrupted, high-leverage focus on core professional or creative work.",
                category = "DAILY",
                statType = "INTELLIGENCE",
                xpReward = 75,
                statBonus = 3,
                requiresCameraProof = true,
                proofType = "STUDY",
                proofInstructions = "Photo of clean workstation, IDE code editor, or project notes",
                dueDate = "TODAY",
                isCountable = true,
                currentProgress = 0,
                targetLimit = 60,
                unit = "mins",
                isCompleted = false,
                streakCount = 0
            ),
            QuestEntity(
                title = "Mindful Reading (20 Pages)",
                description = "Non-fiction, technical learning, or philosophical insight.",
                category = "DAILY",
                statType = "FOCUS",
                xpReward = 50,
                statBonus = 2,
                requiresCameraProof = true,
                proofType = "READING",
                proofInstructions = "Photo of physical book page or e-reader",
                dueDate = "TODAY",
                isCountable = true,
                currentProgress = 0,
                targetLimit = 20,
                unit = "pages",
                isCompleted = false,
                streakCount = 0
            ),

            // 3. Health & Clean Living Protocol (Starts at 0)
            QuestEntity(
                title = "Optimal Hydration (3,000 ml)",
                description = "Cellular hydration for peak cognitive function and metabolic energy.",
                category = "DAILY",
                statType = "HEALTH",
                xpReward = 40,
                statBonus = 1,
                requiresCameraProof = true,
                proofType = "WATER",
                proofInstructions = "Photo of filled water bottle or glass",
                dueDate = "TODAY",
                isCountable = true,
                currentProgress = 0,
                targetLimit = 3000,
                unit = "ml",
                isCompleted = false,
                streakCount = 0
            ),
            QuestEntity(
                title = "Clean Living: Tobacco & Nicotine Free Day",
                description = "100% clean lungs. Zero gutkha, tobacco, cigarette, or vaping lapses.",
                category = "MAIN",
                statType = "DISCIPLINE",
                xpReward = 150,
                statBonus = 5,
                requiresCameraProof = false,
                dueDate = "DAILY PROTOCOL",
                isCompleted = false,
                streakCount = 0
            ),

            // 4. Real-World Challenges & Milestones (Starts at 0)
            QuestEntity(
                title = "30-Day Zero Tobacco / Clean Habit Milestone",
                description = "Achieve 30 consecutive clean days of neuro-chemical dopamine reset.",
                category = "BOSS",
                statType = "DISCIPLINE",
                xpReward = 400,
                statBonus = 8,
                requiresCameraProof = false,
                isBossRaid = true,
                bossName = "30-Day Clean Habit Milestone",
                bossHp = 0,
                maxBossHp = 30,
                bossRewardTitle = "Clean Living Master",
                bossDamagePerTask = 1,
                isCompleted = false
            ),
            QuestEntity(
                title = "100km Monthly Running Challenge",
                description = "Accumulate 100km of aerobic running or walking over the current month.",
                category = "BOSS",
                statType = "HEALTH",
                xpReward = 500,
                statBonus = 10,
                requiresCameraProof = false,
                isBossRaid = true,
                bossName = "100km Monthly Running Challenge",
                bossHp = 0,
                maxBossHp = 100,
                bossRewardTitle = "Endurance Champion",
                bossDamagePerTask = 5,
                isCompleted = false
            )
        )
    }

    fun getInitialSkills(): List<SkillTreeNodeEntity> {
        return listOf(
            SkillTreeNodeEntity(
                id = "will_1",
                name = "120s Craving Pause",
                description = "When a physical craving (gutkha/snack) peaks, deploy 120 seconds of box breathing and cold water.",
                category = "WILLPOWER",
                tier = 1,
                costPoints = 1,
                isUnlocked = true,
                iconName = "shield",
                statBoostDescription = "90% Craving Decay Rate"
            ),
            SkillTreeNodeEntity(
                id = "will_2",
                name = "Habit Stacking Matrix",
                description = "Pair a new challenging habit with an automatic anchor habit (e.g. pushups right after morning brew).",
                category = "WILLPOWER",
                tier = 2,
                costPoints = 2,
                isUnlocked = false,
                iconName = "sword",
                statBoostDescription = "+8 Habit Adherence",
                prerequisiteId = "will_1"
            ),
            SkillTreeNodeEntity(
                id = "will_3",
                name = "Digital Monk Mode",
                description = "Zero notifications and phone kept in another room during scheduled deep work sessions.",
                category = "WILLPOWER",
                tier = 3,
                costPoints = 3,
                isUnlocked = false,
                iconName = "crown",
                statBoostDescription = "+15 Deep Work Output",
                prerequisiteId = "will_2"
            ),
            SkillTreeNodeEntity(
                id = "phys_1",
                name = "Morning Sunlight & Hydration",
                description = "500ml water and 10 minutes of direct sunlight within 30 minutes of waking to set cortisol/melatonin rhythm.",
                category = "PHYSICAL",
                tier = 1,
                costPoints = 1,
                isUnlocked = true,
                iconName = "water",
                statBoostDescription = "+10 Daytime Alertness"
            ),
            SkillTreeNodeEntity(
                id = "phys_2",
                name = "Progressive Overload Tracking",
                description = "Consistently increase daily reps, distance, or reading pages by 2-5% every week.",
                category = "PHYSICAL",
                tier = 2,
                costPoints = 2,
                isUnlocked = false,
                iconName = "muscle",
                statBoostDescription = "+12 Physical Stamina",
                prerequisiteId = "phys_1"
            ),
            SkillTreeNodeEntity(
                id = "mind_1",
                name = "Box Breathing Stress Down-Regulation",
                description = "Inhale 4s, Hold 4s, Exhale 4s, Hold 4s. Instantly reduces sympathetic nervous system arousal.",
                category = "MIND",
                tier = 1,
                costPoints = 1,
                isUnlocked = true,
                iconName = "brain",
                statBoostDescription = "-30% Cortisol Spike"
            )
        )
    }

    fun getInitialChatMessage(): ChatMessageEntity {
        return ChatMessageEntity(
            sender = "SYSTEM_AI",
            message = "Welcome to Day 0. Every great journey starts from Ground Zero. I am your evidence-based habit and performance coach. Today is your foundation: execute your baseline pushups, hydrate properly, and keep your clean streak intact starting from day one. How are you feeling right now?",
            tone = "PERFORMANCE",
            isExcuseAnalysis = false,
            timestamp = System.currentTimeMillis()
        )
    }

    fun getInitialDailyLog(): DailyLifeLogEntity {
        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return DailyLifeLogEntity(
            date = todayStr,
            sleepHours = 7.0f,
            exerciseMinutes = 0,
            studyMinutes = 0,
            workMinutes = 0,
            screenTimeMinutes = 0,
            tobaccoUrgesResisted = 0,
            tobaccoLapses = 0,
            moodRating = 3,
            energyRating = 3,
            waterMl = 0,
            stepCount = 0,
            note = "Day 0: Starting fresh on the discipline protocol."
        )
    }
}
