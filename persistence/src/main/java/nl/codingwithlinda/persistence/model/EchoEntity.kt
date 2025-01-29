package nl.codingwithlinda.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EchoEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val mood: Int,
    val duration: Long,
    val timestamp: Long,
    val soundUri: String

)
