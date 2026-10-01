package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.ChatDao
import com.example.data.local.dao.DailyLogDao
import com.example.data.local.dao.HunterDao
import com.example.data.local.dao.ProofLogDao
import com.example.data.local.dao.SkillTreeDao
import com.example.data.local.dao.QuestDao
import com.example.data.local.dao.VisualProgressDao
import com.example.data.local.entity.ChatMessageEntity
import com.example.data.local.entity.DailyLifeLogEntity
import com.example.data.local.entity.HunterProfileEntity
import com.example.data.local.entity.ProofLogEntity
import com.example.data.local.entity.QuestEntity
import com.example.data.local.entity.SkillTreeNodeEntity
import com.example.data.local.entity.VisualProgressEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        HunterProfileEntity::class,
        QuestEntity::class,
        ProofLogEntity::class,
        DailyLifeLogEntity::class,
        SkillTreeNodeEntity::class,
        ChatMessageEntity::class,
        VisualProgressEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun hunterDao(): HunterDao
    abstract fun questDao(): QuestDao
    abstract fun proofLogDao(): ProofLogDao
    abstract fun dailyLogDao(): DailyLogDao
    abstract fun skillTreeDao(): SkillTreeDao
    abstract fun chatDao(): ChatDao
    abstract fun visualProgressDao(): VisualProgressDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "project_zero_hunter.db"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database)
                    }
                }
            }

            suspend fun populateDatabase(database: AppDatabase) {
                database.hunterDao().insertProfile(DataSeeder.getInitialProfile())
                database.questDao().insertAll(DataSeeder.getInitialQuests())
                database.skillTreeDao().insertAll(DataSeeder.getInitialSkills())
                database.chatDao().insertMessage(DataSeeder.getInitialChatMessage())
                database.dailyLogDao().insertOrUpdate(DataSeeder.getInitialDailyLog())
            }
        }
    }
}
