package nl.codingwithlinda.echojournal.core.domain.data_source.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.echojournal.core.domain.data_source.DataSourceAccess
import nl.codingwithlinda.echojournal.core.domain.model.Echo

class EchoAccess: DataSourceAccess<Echo, String> {

    private val echoes = MutableStateFlow<List<Echo>>(emptyList())
    override suspend fun create(item: Echo): Echo {
        echoes.update {
            it + item
        }
        return item
    }

    override suspend fun read(id: String): Echo? {
        return echoes.value.find { it.id == id }
    }

    override suspend fun update(item: Echo): Echo {
       return item
    }

    override suspend fun delete(id: String): Boolean {
        echoes.update {
            it.filter { it.id != id }
        }
        return true
    }

    override fun readAll(): Flow<List<Echo>> = echoes
}