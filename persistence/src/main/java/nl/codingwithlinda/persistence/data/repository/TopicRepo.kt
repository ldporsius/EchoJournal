package nl.codingwithlinda.persistence.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.codingwithlinda.core.model.Topic
import nl.codingwithlinda.persistence.data.mapping.toDomain
import nl.codingwithlinda.persistence.data.mapping.toEntity
import nl.codingwithlinda.persistence.domain.DataSourceAccess
import nl.codingwithlinda.persistence.room.EchoDatabase

typealias TopicsAccess = DataSourceAccess<Topic, String>
class TopicRepo(
    private val context: Context
): DataSourceAccess<Topic, String> {

    private val database = EchoDatabase.getDatabase(context)
    private val topicDao = database.topicDao
    override suspend fun create(item: Topic): Topic {
        topicDao.create(item.toEntity())
        return item
    }

    override suspend fun read(id: String): Topic? {
        return topicDao.getTopicsForId(id).firstOrNull()?.toDomain()
    }

    override suspend fun update(item: Topic): Topic {
        topicDao.create(item.toEntity())
        return item
    }

    override suspend fun delete(id: String): Boolean {
        topicDao.delete(id)
        return true
    }

    override fun readAll(): Flow<List<Topic>> {
        return topicDao.readAll().map { it.map { it.toDomain() }  }
    }
}