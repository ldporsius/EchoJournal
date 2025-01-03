package nl.codingwithlinda.echojournal.feature_entries.domain.model

data class Echo(
    val id: String,
    val mood: Mood,
    val name: String,
    val description: String,
    val timeStamp: Long,
    val amplitudes: List<Float>,
    val topics: List<String>,
)
