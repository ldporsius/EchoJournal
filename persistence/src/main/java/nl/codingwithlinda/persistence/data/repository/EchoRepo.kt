package nl.codingwithlinda.persistence.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import nl.codingwithlinda.core.model.Echo
import nl.codingwithlinda.persistence.data.mapping.amplitudeToEntity
import nl.codingwithlinda.persistence.data.mapping.toDomain
import nl.codingwithlinda.persistence.data.mapping.toEntity
import nl.codingwithlinda.persistence.domain.DataSourceAccess
import nl.codingwithlinda.persistence.room.EchoDatabase

typealias EchoAccess = DataSourceAccess<Echo, String>
class EchoRepo(
    private val context: Context
): DataSourceAccess<Echo, String> {

    private val database = EchoDatabase.getDatabase(context)
    private val echoDao = database.echoDao
    private val amplitudeDao = database.amplitudeDao
    private val topicDao = database.topicDao


    override suspend fun create(item: Echo): Echo {
       echoDao.create(item.toEntity())
        item.amplitudes.forEach {
            amplitudeDao.create(amplitudeToEntity(item.id, it))
        }
       item.topics.forEach {
           topicDao.create(it.toEntity())
           topicDao.createEchoTopicXref(
               nl.codingwithlinda.persistence.model.EchoTopicXref(
                   echoId = item.id,
                   topicId = it.name
               )
           )
       }
        return item
    }

    override suspend fun read(id: String): Echo? {
       return combineEchoTopicAmplitudes(id)
    }

    override suspend fun update(item: Echo): Echo {
        return item
    }

    override suspend fun delete(id: String): Boolean {
        echoDao.delete(id)
        return true
    }

    override fun readAll(): Flow<List<Echo>> {
       return echoDao.readAll().mapNotNull {
           it.mapNotNull {
              combineEchoTopicAmplitudes(it.id)
           }
       }
    }

    private suspend fun combineEchoTopicAmplitudes(id: String): Echo?{
        val amplitudes = amplitudeDao.read(id).map { it.amplitude }
        val topics = topicDao.getEchoTopicXrefsForEchoId(id).map {
            topicDao.getTopicsForId(it.topicId)
        }.flatMap {
            it.map { topicEntity -> topicEntity.toDomain() }
        }
        return echoDao.read(id)?.toDomain(
            amplitudes = amplitudes,
            topics = topics
        )
    }
}