package nl.codingwithlinda.echojournal.navigation

import kotlinx.serialization.Serializable
import nl.codingwithlinda.echojournal.core.data.EchoDto

interface Route

@Serializable
data object EchosRoute : Route

@Serializable
data class CreateEchoRoute(
    val echoDto: EchoDto
) : Route