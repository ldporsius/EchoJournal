package nl.codingwithlinda.persistence.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.persistence.model.EchoEntity

@Dao
interface EchoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(echo: EchoEntity)

    @Query("SELECT * FROM EchoEntity WHERE id = :id")
    suspend fun read(id: String): EchoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(echo: EchoEntity)

    @Query("DELETE FROM EchoEntity WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM EchoEntity")
    fun readAll(): Flow<List<EchoEntity>>

}