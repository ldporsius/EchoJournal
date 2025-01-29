package nl.codingwithlinda.persistence.model

import androidx.room.Entity

@Entity(primaryKeys = ["echoId", "topicId"])
data class EchoTopicXref(
    val echoId: String,
    val topicId: String
)
