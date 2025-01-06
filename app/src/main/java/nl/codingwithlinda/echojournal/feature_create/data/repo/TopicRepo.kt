package nl.codingwithlinda.echojournal.feature_create.data.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.codingwithlinda.echojournal.core.data.TopicFactory
import nl.codingwithlinda.echojournal.core.domain.data_source.repo.TopicsAccess

class TopicRepo(
    private val topicsAccess: TopicsAccess,
    private val topicFactory: TopicFactory

) {

    suspend fun create(name: String) {
        topicsAccess.create(topicFactory.createTopic(name))
    }

    fun readAll(): Flow<List<String>>{
        return topicsAccess.readAll().map { it.map { it.name } }
    }

}