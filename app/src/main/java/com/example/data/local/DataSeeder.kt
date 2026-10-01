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
            hunterName = "Abhiron (Admin)",
            userEmail = "sabhiron5@gmail.com",
            title = "System Monarch & Creator",
            level = 10,
            currentXp = 250,
            maxXp = 500,
            rank = "MONARCH",
            accountTier = "SYSTEM_ADMIN",
            isAdmin = true,
            godModeEnabled = true,
            hp = 100,
            maxHp = 100,
            mp = 100,
            maxMp = 100,
            strength = 25,
            discipline = 25,
            intelligence = 30,
            focus = 25,
            charisma = 25,
            health = 25,
            availableSkillPoints = 10,
            totalQuestsCompleted = 15,
            currentStreak = 7,
            highestStreak = 14,
            consistencyScore = 98,
            burnoutRisk = "Low",
            tobaccoFreeDays = 7,
            soundEnabled = true,
            hapticsEnabled = true,
            preferredAiTone = "SYSTEM"
        )
    }

    fun getInitialQuests(): List<QuestEntity> {
        return listOf(
            // Solo Leveling Daily Physical Regimen (0 to N Limits)
            QuestEntity(
                title = "Push-ups [Preparation to Become Strong]",
                description = "Complete your daily push-up quota. Increase strength & upper body power.",
                category = "DAILY",
                statType = "STRENGTH",
                xpReward = 100,
                statBonus = 2,
                requiresCameraProof = true,
                proofType = "EXERCISE",
                proofInstructions = "Show workout space or fitness form",
                dueDate = "BEFORE MIDNIGHT",
                isCountable = true,
                currentProgress = 0,
                targetLimit = 100,
                unit = "reps"
            ),
            QuestEntity(
                title = "Sit-ups [Core Awakening]",
                description = "Complete core abdominal crunches or sit-up reps.",
                category = "DAILY",
                statType = "STRENGTH",
                xpReward = 100,
                statBonus = 2,
                requiresCameraProof = false,
                dueDate = "BEFORE MIDNIGHT",
                isCountable = true,
                currentProgress = 0,
                targetLimit = 100,
                unit = "reps"
            ),
            QuestEntity(
                title = "Squats [Iron Foundation]",
                description = "Build lower body explosive power and hunter endurance.",
                category = "DAILY",
                statType = "HEALTH",
                xpReward = 100,
                statBonus = 2,
                requiresCameraProof = false,
                dueDate = "BEFORE MIDNIGHT",
                isCountable = true,
                currentProgress = 0,
                targetLimit = 100,
                unit = "reps"
            ),
            QuestEntity(
                title = "10km Running / 10,000 Steps",
                description = "Cover 10km distance or 10,000 steps of aerobic stamina.",
                category = "DAILY",
                statType = "HEALTH",
                xpReward = 150,
                statBonus = 3,
                requiresCameraProof = true,
                proofType = "WALKING",
                proofInstructions = "Screenshot of step tracker or outdoor running photo",
                dueDate = "BEFORE MIDNIGHT",
                isCountable = true,
                currentProgress = 0,
                targetLimit = 10,
                unit = "km"
            ),
            QuestEntity(
                title = "Hydration Awakening (3,000ml)",
                description = "Daily target hydration for cellular vitality and alertness.",
                category = "DAILY",
                statType = "HEALTH",
                xpReward = 60,
                statBonus = 1,
                requiresCameraProof = true,
                proofType = "WATER",
                proofInstructions = "Photo of water bottle or filled glass",
                dueDate = "TODAY",
                isCountable = true,
                currentProgress = 500,
                targetLimit = 3000,
                unit = "ml"
            ),
            QuestEntity(
                title = "Deep Study & Skill Building (60 Mins)",
                description = "Uninterrupted deep work and learning block.",
                category = "DAILY",
                statType = "INTELLIGENCE",
                xpReward = 90,
                statBonus = 2,
                requiresCameraProof = true,
                proofType = "STUDY",
                proofInstructions = "Photo of notes, book, or IDE workstation",
                dueDate = "TODAY",
                isCountable = true,
                currentProgress = 0,
                targetLimit = 60,
                unit = "mins"
            ),

            // Main Life Transformation Quest
            QuestEntity(
                title = "Main Quest: Shatter The Tobacco Urge (Quit Gutkha)",
                description = "Complete 7 days of 100% tobacco/gutkha-free discipline. Replace cravings with hydration & breathing.",
                category = "MAIN",
                statType = "DISCIPLINE",
                xpReward = 350,
                statBonus = 4,
                requiresCameraProof = false,
                dueDate = "7 DAYS",
                isBossRaid = false
            ),

            // Boss Raids
            QuestEntity(
                title = "Boss Raid: Gate of the Smoke Fiend",
                description = "3 consecutive days with Zero Tobacco/Gutkha lapses. Strike 33 HP per clean day.",
                category = "BOSS",
                statType = "DISCIPLINE",
                xpReward = 500,
                statBonus = 5,
                requiresCameraProof = false,
                isBossRaid = true,
                bossName = "Ignis, The Smoke Fiend",
                bossHp = 70,
                maxBossHp = 100,
                bossRewardTitle = "Addiction Slayer",
                bossDamagePerTask = 35
            ),
            QuestEntity(
                title = "Boss Raid: Baran, Demon King of Sloth",
                description = "Complete all daily quests 5 days in a row to defeat the Demon King.",
                category = "BOSS",
                statType = "FOCUS",
                xpReward = 1000,
                statBonus = 8,
                requiresCameraProof = false,
                isBossRaid = true,
                bossName = "Baran, Demon King of Sloth",
                bossHp = 200,
                maxBossHp = 200,
                bossRewardTitle = "Monarch of Willpower",
                bossDamagePerTask = 40
            )
        )
    }

    fun getInitialSkills(): List<SkillTreeNodeEntity> {
        return listOf(
            SkillTreeNodeEntity(
                id = "will_1",
                name = "Urge Interceptor I",
                description = "Activates a 120-second mindfulness pause when an urge (gutkha/snack) strikes.",
                category = "WILLPOWER",
                tier = 1,
                costPoints = 1,
                isUnlocked = true,
                iconName = "shield",
                statBoostDescription = "+5% Craving Resistance"
            ),
            SkillTreeNodeEntity(
                id = "will_2",
                name = "Addiction Slayer Matrix",
                description = "Double XP awarded for every 24-hour clean streak.",
                category = "WILLPOWER",
                tier = 2,
                costPoints = 2,
                isUnlocked = false,
                iconName = "sword",
                statBoostDescription = "+10 Discipline, 2x Clean XP",
                prerequisiteId = "will_1"
            ),
            SkillTreeNodeEntity(
                id = "will_3",
                name = "Monk Mode Domain",
                description = "Zero notification tolerance during deep focus blocks.",
                category = "WILLPOWER",
                tier = 3,
                costPoints = 3,
                isUnlocked = false,
                iconName = "crown",
                statBoostDescription = "+15 Focus & Willpower",
                prerequisiteId = "will_2"
            ),
            SkillTreeNodeEntity(
                id = "phys_1",
                name = "Hydration Rush",
                description = "Morning 500ml water boosts energy rating by +1 automatically.",
                category = "PHYSICAL",
                tier = 1,
                costPoints = 1,
                isUnlocked = true,
                iconName = "water",
                statBoostDescription = "+5 Health Stat"
            ),
            SkillTreeNodeEntity(
                id = "phys_2",
                name = "Iron Muscle Protocol",
                description = "Gain +20% bonus XP from all verified exercise camera proofs.",
                category = "PHYSICAL",
                tier = 2,
                costPoints = 2,
                isUnlocked = false,
                iconName = "fitness",
                statBoostDescription = "+8 Strength Stat",
                prerequisiteId = "phys_1"
            ),
            SkillTreeNodeEntity(
                id = "phys_3",
                name = "Shadow Hunter Stamina",
                description = "Reduces fatigue burnout meter by 50%.",
                category = "PHYSICAL",
                tier = 3,
                costPoints = 3,
                isUnlocked = false,
                iconName = "bolt",
                statBoostDescription = "+12 Health & Endurance",
                prerequisiteId = "phys_2"
            ),
            SkillTreeNodeEntity(
                id = "ment_1",
                name = "Hyperfocus Pulse",
                description = "45-minute Pomodoro protocol with ambient noise suppression.",
                category = "MENTAL",
                tier = 1,
                costPoints = 1,
                isUnlocked = false,
                iconName = "psychology",
                statBoostDescription = "+5 Focus Stat"
            ),
            SkillTreeNodeEntity(
                id = "ment_2",
                name = "Grimoire Mastery",
                description = "AI auto-summarizes core takeaways from verified reading proofs.",
                category = "MENTAL",
                tier = 2,
                costPoints = 2,
                isUnlocked = false,
                iconName = "book",
                statBoostDescription = "+8 Intelligence Stat",
                prerequisiteId = "ment_1"
            ),
            SkillTreeNodeEntity(
                id = "know_1",
                name = "AI Life Strategist",
                description = "Unlocks detailed weekly AI habit pattern diagnostic reports.",
                category = "KNOWLEDGE",
                tier = 1,
                costPoints = 1,
                isUnlocked = true,
                iconName = "analytics",
                statBoostDescription = "+5 Charisma & Intellect"
            )
        )
    }

    fun getInitialChatMessage(): ChatMessageEntity {
        return ChatMessageEntity(
            sender = "SYSTEM_AI",
            message = "【 SYSTEM AWAKENING COMPLETE 】\n\nPlayer Sung Jin-Woo registered at Rank E (Level 1).\n\nYour life is now managed by the Solo Leveling Life System. Complete your daily push-ups, squats, running, and water goals [0/N]. Upload camera proof to verify your discipline.\n\nWarning: Failure to complete daily exercises will invoke a penalty quest. State your goal to begin.",
            tone = "SYSTEM",
            timestamp = System.currentTimeMillis()
        )
    }

    fun getInitialDailyLog(): DailyLifeLogEntity {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = sdf.format(Date())
        return DailyLifeLogEntity(
            date = today,
            sleepHours = 7.5f,
            exerciseMinutes = 30,
            studyMinutes = 60,
            workMinutes = 360,
            screenTimeMinutes = 150,
            tobaccoUrgesResisted = 3,
            tobaccoLapses = 0,
            moodRating = 4,
            energyRating = 4,
            waterMl = 2200,
            stepCount = 6400,
            note = "First day in Project Zero. Push-up and squat reps started.",
            aiDailyReport = "Daily System protocol initiated. 0/100 exercise progressions tracked. Keep pushing reps."
        )
    }
}
