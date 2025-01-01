package nl.codingwithlinda.echojournal.navigation

import kotlinx.serialization.Serializable

interface Route

@Serializable
data object EchosRoute : Route