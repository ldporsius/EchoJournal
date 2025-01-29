package nl.codingwithlinda.echojournal.feature_create.data.repo

import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.echojournal.core.data.TopicFactory
import nl.codingwithlinda.core.model.Topic
import nl.codingwithlinda.persistence.domain.DataSourceAccess

class TopicRepo(
    private val topicsAccess: DataSourceAccess<Topic, String>,
    private val topicFactory: TopicFactory
) {

    suspend fun create(name: String): Topic {
       return topicsAccess.create(topicFactory.createTopic(name))
    }

    suspend fun delete(id: String) {
        topicsAccess.delete(id)
    }

    fun readAll(): Flow<List<Topic>>{
        return topicsAccess.readAll()
    }

}