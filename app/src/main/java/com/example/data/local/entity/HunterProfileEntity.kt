package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hunter_profile")
data class HunterProfileEntity(
    @PrimaryKey val id: Int = 1,
    val hunterName: String = "Sung Jin-Woo",
    val title: String = "The Awakened Zero",
    val level: Int = 1,
    val currentXp: Int = 0,
    val maxXp: Int = 100,
    val rank: String = "E", // E, D, C, B, A, S, National, Monarch
    val hp: Int = 100,
    val maxHp: Int = 100,
    val mp: Int = 50,
    val maxMp: Int = 50,
    // Core Hunter Stats
    val strength: Int = 10,
    val discipline: Int = 10,
    val intelligence: Int = 10,
    val focus: Int = 10,
    val charisma: Int = 10,
    val health: Int = 10,
    // Game progression
    val availableSkillPoints: Int = 2,
    val totalQuestsCompleted: Int = 0,
    val currentStreak: Int = 1,
    val highestStreak: Int = 1,
    val consistencyScore: Int = 100, // 0 - 100
    val burnoutRisk: String = "Low", // Low, Moderate, High
    val tobaccoFreeDays: Int = 0,
    val soundEnabled: Boolean = true,
    val hapticsEnabled: Boolean = true,
    val preferredAiTone: String = "SYSTEM" // SYSTEM, IRON_COACH, WISE_MENTOR
)
