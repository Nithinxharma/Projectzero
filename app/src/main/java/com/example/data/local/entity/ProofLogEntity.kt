package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "proof_logs")
data class ProofLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val questId: Long,
    val questTitle: String,
    val proofType: String,
    val imagePath: String, // local path or cache URI
    val verificationStatus: String, // VERIFIED, REJECTED, EVALUATING
    val aiFeedback: String,
    val confidenceScore: Int, // 0 - 100
    val xpAwarded: Int,
    val statAwarded: String,
    val timestamp: Long = System.currentTimeMillis()
)
