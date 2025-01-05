package nl.codingwithlinda.echojournal.feature_create.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.feature_create.data.repo.TopicRepo
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState

class CreateEchoViewModel(
    val topicRepo: TopicRepo
): ViewModel() {

    private val topicsSearchText = MutableStateFlow("")
    private val _allTopics = topicRepo.readAll()
    private val filteredTopics = _allTopics.combine(topicsSearchText) { topics , search ->
        topics.filter { it.contains(search, ignoreCase = true) }
    }

    private val _uiState = MutableStateFlow(CreateEchoUiState())
    val uiState = combine( _uiState, filteredTopics, topicsSearchText) { uiState, filteredTopics, search ->
        uiState.copy(
            topic = search,
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
            }

           is CreateEchoAction.ShowHideTopics -> {
               println("SHOW HIDE TOPICS CALLED: VISIBLE = ${action.visible}")
                _uiState.update {
                    it.copy(
                        isTopicsExpanded = action.visible
                    )
                }
            }
            is CreateEchoAction.SelectTopic -> {
                topicsSearchText.update {
                    action.topic
                }
            }
            is CreateEchoAction.CreateTopic -> {
                viewModelScope.launch {
                    topicRepo.create(action.topic)
                }
            }
        }
    }
}