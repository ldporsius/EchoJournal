package nl.codingwithlinda.echojournal.feature_entries.presentation.state

import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.core.presentation.util.UiText

data class TopicsUiState(
    val topics: List<Topic> = emptyList(),
    val selectedTopics: List<Topic> = emptyList(),
    val shouldShowClearSelection: Boolean = false,
    val selectedTopicsUiText: UiText = UiText.StringResource(R.string.all_topics),
    )
