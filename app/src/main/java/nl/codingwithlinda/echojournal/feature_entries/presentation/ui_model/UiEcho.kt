package nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model

import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.feature_create.presentation.state.PlaybackState

data class UiEcho(
    val id: String,
    val uri: String,
    val mood: UiMood,
    val name: String,
    val timeStamp: String,
    val description: String,
    val amplitudes: List<Float>,
    val duration: String,
    val topics: List<Topic>,
){
    fun playbackState( state : PlaybackState): PlaybackState {
        return state
    }
}
