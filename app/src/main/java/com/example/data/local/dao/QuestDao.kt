package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.QuestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {
    @Query("SELECT * FROM quests ORDER BY isCompleted ASC, isBossRaid DESC, id ASC")
    fun getAllQuests(): Flow<List<QuestEntity>>

    @Query("SELECT * FROM quests WHERE category = :category ORDER BY isCompleted ASC, id ASC")
    fun getQuestsByCategory(category: String): Flow<List<QuestEntity>>

    @Query("SELECT * FROM quests WHERE isBossRaid = 1 ORDER BY isCompleted ASC")
    fun getBossRaids(): Flow<List<QuestEntity>>

    @Query("SELECT * FROM quests WHERE id = :id")
    suspend fun getQuestById(id: Long): QuestEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuest(quest: QuestEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quests: List<QuestEntity>)

    @Update
    suspend fun updateQuest(quest: QuestEntity)

    @Delete
    suspend fun deleteQuest(quest: QuestEntity)

    @Query("UPDATE quests SET isCompleted = 0 WHERE category = 'DAILY'")
    suspend fun resetDailyQuests()
}
