package nl.codingwithlinda.echojournal.core.domain.model

data class Echo(
    val id: String,
    val mood: Mood,
    val title: String,
    val description: String,
    val timeStamp: Long,
    val amplitudes: List<Float>,
    val topics: List<Topic>,
    val uri: String
)
