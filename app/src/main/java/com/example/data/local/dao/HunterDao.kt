package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.HunterProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HunterDao {
    @Query("SELECT * FROM hunter_profile WHERE id = 1")
    fun getProfile(): Flow<HunterProfileEntity?>

    @Query("SELECT * FROM hunter_profile WHERE id = 1")
    suspend fun getProfileOnce(): HunterProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: HunterProfileEntity)

    @Update
    suspend fun updateProfile(profile: HunterProfileEntity)
}
