package nl.codingwithlinda.persistence.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.persistence.model.EchoTopicXref
import nl.codingwithlinda.persistence.model.TopicEntity

@Dao
interface TopicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(topic: TopicEntity)

    @Query("DELETE FROM TopicEntity WHERE name = :topicId")
    suspend fun delete(topicId: String)

    @Query("SELECT * FROM EchoTopicXref WHERE echoId = :echoId")
    suspend fun getEchoTopicXrefsForEchoId(echoId: String): List<EchoTopicXref>

    @Query("SELECT * FROM TopicEntity WHERE name = :topicId")
    suspend fun getTopicsForId(topicId: String): List<TopicEntity>

    @Query("SELECT * FROM TopicEntity")
    fun readAll(): Flow<List<TopicEntity>>
}