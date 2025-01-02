package nl.codingwithlinda.echojournal.feature_entries.presentation.state

import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic

sealed interface FilterEchoAction {
    data class ToggleSelectTopic(val topic: UiTopic) : FilterEchoAction
    data object ClearTopicSelection : FilterEchoAction
}