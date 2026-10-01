package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_life_logs")
data class DailyLifeLogEntity(
    @PrimaryKey val date: String, // "YYYY-MM-DD"
    val sleepHours: Float = 7.5f,
    val exerciseMinutes: Int = 30,
    val studyMinutes: Int = 60,
    val workMinutes: Int = 360,
    val screenTimeMinutes: Int = 180,
    val tobaccoUrgesResisted: Int = 0,
    val tobaccoLapses: Int = 0,
    val moodRating: Int = 4, // 1 to 5
    val energyRating: Int = 4, // 1 to 5
    val waterMl: Int = 2500,
    val stepCount: Int = 6000,
    val note: String = "",
    val aiDailyReport: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
