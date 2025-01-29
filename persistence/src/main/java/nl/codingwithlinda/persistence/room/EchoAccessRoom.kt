package nl.codingwithlinda.persistence.room

import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.persistence.domain.DataSourceAccess
import nl.codingwithlinda.persistence.model.EchoEntity
import nl.codingwithlinda.persistence.room.dao.EchoDao

class EchoAccessRoom(
    private val echoDao: EchoDao
): DataSourceAccess<EchoEntity, String> {
    override suspend fun create(item: EchoEntity): EchoEntity {
        echoDao.create(item)
        return item
    }

    override suspend fun read(id: String): EchoEntity? {
        return echoDao.read(id)
    }

    override suspend fun update(item: EchoEntity): EchoEntity {
       echoDao.update(item)
        return item
    }

    override suspend fun delete(id: String): Boolean {
        echoDao.delete(id)
        return true
    }

    override fun readAll(): Flow<List<EchoEntity>> {
        return echoDao.readAll()
    }

}