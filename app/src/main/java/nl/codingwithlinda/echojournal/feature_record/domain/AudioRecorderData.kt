package nl.codingwithlinda.echojournal.feature_record.domain

data class AudioRecorderData(
    val duration: Long = 0L,
    val uri: String = "",
    val amplitudesUri: String
)
