package com.example.data.repository

import com.example.data.local.dao.ChatDao
import com.example.data.local.dao.DailyLogDao
import com.example.data.local.dao.HunterDao
import com.example.data.local.dao.ProofLogDao
import com.example.data.local.dao.QuestDao
import com.example.data.local.dao.SkillTreeDao
import com.example.data.local.dao.VisualProgressDao
import com.example.data.local.entity.ChatMessageEntity
import com.example.data.local.entity.DailyLifeLogEntity
import com.example.data.local.entity.HunterProfileEntity
import com.example.data.local.entity.ProofLogEntity
import com.example.data.local.entity.QuestEntity
import com.example.data.local.entity.SkillTreeNodeEntity
import com.example.data.local.entity.VisualProgressEntity
import kotlinx.coroutines.flow.Flow

class HunterRepository(
    private val hunterDao: HunterDao,
    private val questDao: QuestDao,
    private val proofLogDao: ProofLogDao,
    private val dailyLogDao: DailyLogDao,
    private val skillTreeDao: SkillTreeDao,
    private val chatDao: ChatDao,
    private val visualProgressDao: VisualProgressDao
) {
    val profile: Flow<HunterProfileEntity?> = hunterDao.getProfile()
    val allQuests: Flow<List<QuestEntity>> = questDao.getAllQuests()
    val bossRaids: Flow<List<QuestEntity>> = questDao.getBossRaids()
    val proofLogs: Flow<List<ProofLogEntity>> = proofLogDao.getAllLogs()
    val dailyLogs: Flow<List<DailyLifeLogEntity>> = dailyLogDao.getAllDailyLogs()
    val skills: Flow<List<SkillTreeNodeEntity>> = skillTreeDao.getAllSkills()
    val chatMessages: Flow<List<ChatMessageEntity>> = chatDao.getAllMessages()
    val visualProgress: Flow<List<VisualProgressEntity>> = visualProgressDao.getAllVisualProgress()

    suspend fun getProfileOnce(): HunterProfileEntity? = hunterDao.getProfileOnce()

    suspend fun updateProfile(profile: HunterProfileEntity) = hunterDao.updateProfile(profile)

    suspend fun insertQuest(quest: QuestEntity): Long = questDao.insertQuest(quest)

    suspend fun updateQuest(quest: QuestEntity) = questDao.updateQuest(quest)

    suspend fun deleteQuest(quest: QuestEntity) = questDao.deleteQuest(quest)

    suspend fun getQuestById(id: Long): QuestEntity? = questDao.getQuestById(id)

    suspend fun logProof(proof: ProofLogEntity): Long = proofLogDao.insertLog(proof)

    suspend fun saveDailyLog(log: DailyLifeLogEntity) = dailyLogDao.insertOrUpdate(log)

    suspend fun getDailyLogForDate(date: String): DailyLifeLogEntity? = dailyLogDao.getLogForDateOnce(date)

    suspend fun unlockSkill(skillId: String): Boolean {
        val currentProfile = hunterDao.getProfileOnce() ?: return false
        val allSkills = skillTreeDao.getSkillsByCategory("ALL") // Or retrieve and match
        // Update skill in DB
        return true
    }

    suspend fun updateSkill(skill: SkillTreeNodeEntity) = skillTreeDao.updateSkill(skill)

    suspend fun insertChatMessage(message: ChatMessageEntity): Long = chatDao.insertMessage(message)

    suspend fun clearChat() = chatDao.clearChat()

    suspend fun insertVisualProgress(progress: VisualProgressEntity): Long = visualProgressDao.insert(progress)
}
