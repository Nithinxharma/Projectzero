package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "skill_tree")
data class SkillTreeNodeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val category: String, // PHYSICAL, MENTAL, WILLPOWER, KNOWLEDGE
    val tier: Int, // 1 to 4
    val costPoints: Int,
    val isUnlocked: Boolean = false,
    val iconName: String,
    val statBoostDescription: String,
    val prerequisiteId: String? = null
)
