package nl.codingwithlinda.echojournal.core.domain.data_source.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import nl.codingwithlinda.echojournal.core.domain.data_source.DataSourceAccess
import nl.codingwithlinda.echojournal.core.domain.model.EchoTopic
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeUiTopics

class TopicsAccess: DataSourceAccess<EchoTopic, String> {

    val fakes = fakeUiTopics().map {
        EchoTopic(it.name)
    }
    private val topics: MutableList<EchoTopic> = mutableListOf<EchoTopic>().apply {
        addAll(fakes)
    }


    override suspend fun create(item: EchoTopic): EchoTopic {
        topics.add(item)
       return item
    }

    override suspend fun read(id: String): EchoTopic? {
       return topics.find { it.name == id }
    }

    override suspend fun update(item: EchoTopic): EchoTopic {
        topics.removeIf { it.name == item.name }
        topics.add(item)
        return item
    }

    override suspend fun delete(id: String): Boolean {
        topics.removeIf { it.name == id }
       return true
    }

    override fun readAll(): Flow<List<EchoTopic>> {
       return flowOf(topics)
    }
}