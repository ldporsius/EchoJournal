package nl.codingwithlinda.echojournal.core.data

import kotlinx.serialization.Serializable

@Serializable
data class EchoDto(
    val amplitudes: List<Float>,
    val uri: String
)
