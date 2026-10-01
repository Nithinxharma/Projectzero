package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quests")
data class QuestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val category: String, // MAIN, DAILY, BOSS, SHADOW, RECOVERY
    val statType: String, // STRENGTH, DISCIPLINE, INTELLIGENCE, FOCUS, CHARISMA, HEALTH
    val xpReward: Int,
    val statBonus: Int = 1,
    val requiresCameraProof: Boolean = false,
    val proofType: String = "NONE", // WATER, READING, EXERCISE, ROOM_CLEANING, STUDY, WALKING, POSTURE, CUSTOM
    val proofInstructions: String = "",
    val isCompleted: Boolean = false,
    val completedAt: Long? = null,
    val streakCount: Int = 0,
    val dueDate: String = "", // e.g. "TODAY", "3 DAYS", "PERMANENT"
    // Daily 0 to N Exercise Reps & Limits
    val isCountable: Boolean = false,
    val currentProgress: Int = 0,
    val targetLimit: Int = 100,
    val unit: String = "reps", // reps, km, ml, mins, pages
    // For Boss Raids
    val isBossRaid: Boolean = false,
    val bossName: String = "",
    val bossHp: Int = 0,
    val maxBossHp: Int = 0,
    val bossRewardTitle: String = "",
    val bossDamagePerTask: Int = 25
)
