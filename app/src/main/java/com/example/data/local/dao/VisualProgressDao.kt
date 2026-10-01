package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.VisualProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VisualProgressDao {
    @Query("SELECT * FROM visual_progress ORDER BY timestamp DESC")
    fun getAllVisualProgress(): Flow<List<VisualProgressEntity>>

    @Query("SELECT * FROM visual_progress WHERE category = :category ORDER BY timestamp DESC")
    fun getVisualProgressByCategory(category: String): Flow<List<VisualProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: VisualProgressEntity): Long
}
