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
            level = 14,
            currentXp = 420,
            maxXp = 600,
            rank = "MASTERY",
            accountTier = "PERFORMANCE_PRO",
            isAdmin = true,
            godModeEnabled = true,
            hp = 100,
            maxHp = 100,
            mp = 100,
            maxMp = 100,
            strength = 28,
            discipline = 32,
            intelligence = 30,
            focus = 29,
            charisma = 26,
            health = 31,
            availableSkillPoints = 6,
            totalQuestsCompleted = 48,
            currentStreak = 14,
            highestStreak = 30,
            consistencyScore = 96,
            burnoutRisk = "Low",
            tobaccoFreeDays = 14,
            soundEnabled = true,
            hapticsEnabled = true,
            preferredAiTone = "PERFORMANCE"
        )
    }

    fun getInitialQuests(): List<QuestEntity> {
        return listOf(
            // 1. Daily Physical Regimen
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
                currentProgress = 20,
                targetLimit = 50,
                unit = "reps"
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
                currentProgress = 30,
                targetLimit = 75,
                unit = "reps"
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
                unit = "reps"
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
                currentProgress = 3,
                targetLimit = 5,
                unit = "km"
            ),

            // 2. Cognitive & Deep Work Habits
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
                currentProgress = 45,
                targetLimit = 60,
                unit = "mins"
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
                currentProgress = 10,
                targetLimit = 20,
                unit = "pages"
            ),

            // 3. Health & Clean Living Protocol
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
                currentProgress = 1500,
                targetLimit = 3000,
                unit = "ml"
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
                isCompleted = true,
                streakCount = 14
            ),

            // 4. Real-World Challenges & Milestones
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
                bossHp = 16,
                maxBossHp = 30,
                bossRewardTitle = "Clean Living Master",
                bossDamagePerTask = 1
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
                bossHp = 58,
                maxBossHp = 100,
                bossRewardTitle = "Endurance Champion",
                bossDamagePerTask = 5
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
                isUnlocked = true,
                iconName = "sword",
                statBoostDescription = "+8 Habit Adherence"
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
            message = "Welcome to your performance protocol. Your streak is at 14 clean days. I am your evidence-based habit and performance coach. Today we focus on hydration, your 50 pushups, and unbroken focus. How is your energy level right now?",
            tone = "PERFORMANCE",
            isExcuseAnalysis = false,
            timestamp = System.currentTimeMillis()
        )
    }

    fun getInitialDailyLog(): DailyLifeLogEntity {
        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return DailyLifeLogEntity(
            date = todayStr,
            sleepHours = 7.5f,
            exerciseMinutes = 30,
            studyMinutes = 60,
            workMinutes = 360,
            screenTimeMinutes = 120,
            tobaccoUrgesResisted = 2,
            tobaccoLapses = 0,
            moodRating = 4,
            energyRating = 4,
            waterMl = 2500,
            stepCount = 8000,
            note = "Completed morning run and deep work session without distractions. Felt calm and focused."
        )
    }
}
