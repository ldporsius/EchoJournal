package nl.codingwithlinda.echojournal.core.data

import kotlinx.serialization.Serializable

@Serializable
data class EchoDto(
    val duration: Long,
    val uri: String,
    val amplitudesUri: String
)
