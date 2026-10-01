package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sender: String, // USER, SYSTEM_AI
    val message: String,
    val tone: String = "SYSTEM", // SYSTEM, IRON_COACH, WISE_MENTOR
    val isExcuseAnalysis: Boolean = false,
    val isBurnoutAlert: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
