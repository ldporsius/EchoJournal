package nl.codingwithlinda.echojournal.feature_entries.domain.model

data class Echo(
    val mood: Mood,
    val name: String,
    val timeStamp: Long,
    val amplitudes: List<Float>,
)
