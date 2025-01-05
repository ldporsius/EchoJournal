package nl.codingwithlinda.echojournal.feature_create.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.echojournal.core.domain.data_source.repo.TopicsAccess
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState

class CreateEchoViewModel(
    val topicsAccess: TopicsAccess
): ViewModel() {

    private val _uiState = MutableStateFlow(CreateEchoUiState())
    val uiState = _uiState
        .onStart {
            _uiState.update {
                it.copy(
                    topics = topicsAccess.readAll().first().map { it.name }
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


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