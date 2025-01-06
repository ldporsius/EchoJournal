package nl.codingwithlinda.echojournal.core.domain.data_source.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.echojournal.core.domain.data_source.DataSourceAccess
import nl.codingwithlinda.echojournal.core.domain.model.EchoTopic

class TopicsAccess: DataSourceAccess<EchoTopic, String> {

    private val text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val fakes = List(5){
        EchoTopic(text.substring(0, 5 + it))
    }
    private val topics = MutableStateFlow<List<EchoTopic>>(fakes)


    override suspend fun create(item: EchoTopic): EchoTopic {
        topics.update {
            it + item
        }
       return item
    }

    override suspend fun read(id: String): EchoTopic? {
       return topics.first().find { it.name == id }
    }

    override suspend fun update(item: EchoTopic): EchoTopic {

        return item
    }

    override suspend fun delete(id: String): Boolean {

       return true
    }

    override fun readAll(): Flow<List<EchoTopic>> {
       return topics
    }
}