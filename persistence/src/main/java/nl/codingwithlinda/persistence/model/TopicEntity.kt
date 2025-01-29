package nl.codingwithlinda.persistence.model

import androidx.room.Entity

@Entity
    (primaryKeys = ["name"])
data class TopicEntity(
    val name: String,
)
