package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.DailyLifeLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyLogDao {
    @Query("SELECT * FROM daily_life_logs ORDER BY date DESC")
    fun getAllDailyLogs(): Flow<List<DailyLifeLogEntity>>

    @Query("SELECT * FROM daily_life_logs WHERE date = :date")
    fun getLogForDate(date: String): Flow<DailyLifeLogEntity?>

    @Query("SELECT * FROM daily_life_logs WHERE date = :date")
    suspend fun getLogForDateOnce(date: String): DailyLifeLogEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(log: DailyLifeLogEntity)

    @Update
    suspend fun update(log: DailyLifeLogEntity)
}
