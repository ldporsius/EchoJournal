package nl.codingwithlinda.echojournal.feature_create.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.echojournal.core.domain.data_source.repo.TopicsAccess
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState

class CreateEchoViewModel(
    val topicsAccess: TopicsAccess
): ViewModel() {

    private val topicsSearchText = MutableStateFlow("")
    private val _allTopics = topicsAccess.readAll().map { it.map { it.name } }
    private val filteredTopics = _allTopics.combine(topicsSearchText) { topics , search ->
        topics.filter { it.contains(search, ignoreCase = true) }
    }
    private val _uiState = MutableStateFlow(CreateEchoUiState())
    val uiState = combine( _uiState, filteredTopics) { uiState, filteredTopics ->
        uiState.copy(
            topics = filteredTopics
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


    fun onAction(action: CreateEchoAction) {
        when (action) {
            is CreateEchoAction.TitleChanged -> {
                _uiState.update {
                    it.copy(
                        title = action.title
                    )
                }
            }

            is CreateEchoAction.TopicChanged -> {
                topicsSearchText.update { action.topic }
                _uiState.update {
                    it.copy(
                        topic = action.topic,
                        isTopicsExpanded = true
                    )
                }
            }
        }
    }
}