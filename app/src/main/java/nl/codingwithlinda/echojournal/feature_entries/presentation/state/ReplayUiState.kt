package nl.codingwithlinda.echojournal.feature_entries.presentation.state

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import nl.codingwithlinda.core.model.Mood
import nl.codingwithlinda.echojournal.core.presentation.mappers.toColor
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterDuration
import nl.codingwithlinda.echojournal.feature_create.presentation.state.PlaybackState

data class ReplayUiState(
    val playbackState: PlaybackState = PlaybackState.STOPPED,
    val mood: Mood = Mood.NEUTRAL,
    val waves: List<Int> = emptyList(),
    val duration: Long = 1L,
    val amplitudesSize: Int = 1
){
    fun amplitudeColor(index: Int): Color {
        return if (index in waves) {
            Color(mood.toColor())
        } else {
            Color(mood.toColor()).copy(.20f).compositeOver(Color.Gray)
        }
    }

    fun backgroundColor(): Color {
       return Color(mood.toColor()).copy(.50f).compositeOver(Color.Gray).copy(.10f)
    }

    fun playbackProgress(): String = DateTimeFormatterDuration.formatDurationProgress(waves.size.toFloat() / amplitudesSize.toFloat(), duration)
}
