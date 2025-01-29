package nl.codingwithlinda.persistence.data.mapping

import nl.codingwithlinda.core.model.Topic
import nl.codingwithlinda.persistence.model.TopicEntity

fun TopicEntity.toDomain(): Topic{
    return Topic(
        name = this.name
    )
}

fun Topic.toEntity(): TopicEntity{
    return TopicEntity(
        name = this.name
    )
}