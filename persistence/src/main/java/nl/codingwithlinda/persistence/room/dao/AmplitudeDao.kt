package nl.codingwithlinda.persistence.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import nl.codingwithlinda.persistence.model.AmplitudeEntity

@Dao
interface AmplitudeDao {
    @Upsert
    suspend fun create(amplitude: AmplitudeEntity)

    @Query("SELECT * FROM AmplitudeEntity WHERE echoId = :echoId")
    suspend fun read(echoId: String): List<AmplitudeEntity>
}