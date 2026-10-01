package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visual_progress")
data class VisualProgressEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val category: String, // FACE, POSTURE, ROOM, PHYSIQUE
    val imagePath: String,
    val aiEvaluation: String,
    val scoreRating: Int, // 1 - 100
    val timestamp: Long = System.currentTimeMillis()
)
