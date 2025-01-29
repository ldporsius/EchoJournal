package nl.codingwithlinda.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.Flow

@Entity
data class AmplitudeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val echoId: String,
    val amplitude: Float,
)
