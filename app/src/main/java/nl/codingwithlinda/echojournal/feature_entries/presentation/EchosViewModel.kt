package nl.codingwithlinda.echojournal.feature_entries.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeUiTopics
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.FilterEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.limitTopics

class EchosViewModel(

): ViewModel() {

    private val fakeTopics = fakeUiTopics(false)
    private val _topics = MutableStateFlow<List<UiTopic>>(fakeTopics)

    private val _topicsUiState = MutableStateFlow(TopicsUiState(
        topics = fakeTopics
    ))
    val topicsUiState = combine(_topics, _topicsUiState){topics, topicsUiState ->

        val selectedTopics = topics.filter { it.isSelected }
        topicsUiState.copy(
            topics = topics,
            selectedTopics = limitTopics(selectedTopics),
            shouldShowClearSelection = selectedTopics.isNotEmpty()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _topicsUiState.value)

    fun onFilterAction(action: FilterEchoAction){
        when(action){
            is FilterEchoAction.ToggleSelectTopic -> {
                println("ECHOS VIEW MODEL TOGGLE TOPIC: ${action.topic.name} is selected: ${action.topic.isSelected}")
                _topics.update {
                    it.map { topic ->
                        if (topic.name == action.topic.name) {
                            topic.copy(isSelected = !topic.isSelected)
                        } else topic
                    }
                }
            }

            FilterEchoAction.ClearTopicSelection -> {
                _topics.update {topics ->
                    topics.map {
                        it.copy(isSelected = false)
                    }
                }
            }
        }
    }
}