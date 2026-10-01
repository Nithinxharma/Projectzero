package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hunter_profile")
data class HunterProfileEntity(
    @PrimaryKey val id: Int = 1,
    val hunterName: String = "Abhiron (Admin)",
    val userEmail: String = "sabhiron5@gmail.com",
    val title: String = "System Monarch & Creator",
    val level: Int = 1,
    val currentXp: Int = 35,
    val maxXp: Int = 100,
    val rank: String = "MONARCH", // E, D, C, B, A, S, National, Monarch
    val accountTier: String = "SYSTEM_ADMIN", // SYSTEM_ADMIN, MONARCH_VIP, HUNTER_PRO
    val isAdmin: Boolean = true,
    val godModeEnabled: Boolean = true,
    val hp: Int = 100,
    val maxHp: Int = 100,
    val mp: Int = 100,
    val maxMp: Int = 100,
    // Core Hunter Stats
    val strength: Int = 25,
    val discipline: Int = 25,
    val intelligence: Int = 28,
    val focus: Int = 25,
    val charisma: Int = 25,
    val health: Int = 25,
    // Game progression
    val availableSkillPoints: Int = 10,
    val totalQuestsCompleted: Int = 12,
    val currentStreak: Int = 7,
    val highestStreak: Int = 14,
    val consistencyScore: Int = 98, // 0 - 100
    val burnoutRisk: String = "Low", // Low, Moderate, High
    val tobaccoFreeDays: Int = 7,
    val soundEnabled: Boolean = true,
    val hapticsEnabled: Boolean = true,
    val preferredAiTone: String = "SYSTEM" // SYSTEM, IRON_COACH, WISE_MENTOR
)
