package nl.codingwithlinda.echojournal.feature_entries.presentation.state

import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.core.presentation.util.UiText
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic

data class TopicsUiState(
    val topics: List<UiTopic> = emptyList(),
    val shouldShowClearSelection: Boolean = false,
    val selectedTopics: UiText = UiText.StringResource(R.string.all_topics),
    )
