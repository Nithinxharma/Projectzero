package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.ProofLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProofLogDao {
    @Query("SELECT * FROM proof_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<ProofLogEntity>>

    @Query("SELECT * FROM proof_logs WHERE questId = :questId ORDER BY timestamp DESC")
    fun getLogsForQuest(questId: Long): Flow<List<ProofLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: ProofLogEntity): Long
}
