package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.SkillTreeNodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillTreeDao {
    @Query("SELECT * FROM skill_tree ORDER BY tier ASC, costPoints ASC")
    fun getAllSkills(): Flow<List<SkillTreeNodeEntity>>

    @Query("SELECT * FROM skill_tree WHERE category = :category ORDER BY tier ASC")
    fun getSkillsByCategory(category: String): Flow<List<SkillTreeNodeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(skills: List<SkillTreeNodeEntity>)

    @Update
    suspend fun updateSkill(skill: SkillTreeNodeEntity)
}
