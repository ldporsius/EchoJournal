package nl.codingwithlinda.echojournal.feature_entries.presentation.state

import nl.codingwithlinda.core.model.Topic
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood

sealed interface FilterEchoAction {
    data class ToggleSelectTopic(val topic: Topic) : FilterEchoAction
    data object ClearTopicSelection : FilterEchoAction
    data class ToggleSelectMood(val mood: UiMood) : FilterEchoAction
    data object ClearMoodSelection : FilterEchoAction
}