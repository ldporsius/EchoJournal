package nl.codingwithlinda.echojournal.core.domain.data_source

import kotlinx.coroutines.flow.Flow

interface DataSourceAccess<T, ID> {
    suspend fun create(item: T): T
    suspend fun read(id: ID): T?
    suspend fun update(item: T): T
    suspend fun delete(id: ID): Boolean

    fun readAll(): Flow<List<T>>

}