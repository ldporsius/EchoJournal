package nl.codingwithlinda.echojournal.feature_create.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.presentation.util.blankMoods
import nl.codingwithlinda.echojournal.feature_create.data.repo.TopicRepo
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState
import nl.codingwithlinda.echojournal.feature_create.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.moodToColorMap

class CreateEchoViewModel(
    val topicRepo: TopicRepo
): ViewModel() {

    private val _topicsSearchText = MutableStateFlow("")

    private val _filteredTopics = _topicsSearchText.combine(topicRepo.readAll()){search , topics->
        if (search.isBlank()) topics

        else {
            topics.filter {
                it.contains(search, ignoreCase = true)
            }
        }
    }
    private val topicsVisible = MutableStateFlow(false)
    val topicsUiState = combine(_filteredTopics, _topicsSearchText, topicsVisible) { topics, search , visible ->
        TopicsUiState(
            topics = topics,
            searchText = search,
            isExpanded = visible
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TopicsUiState())

    val selectedTopics = MutableStateFlow<List<String>>(emptyList())

    private val _selectedMood = MutableStateFlow<UiMood?>(null)
    private val coloredMoods = moodToColorMap
    private val _moods = _selectedMood.map {selectedMood ->
        if (selectedMood == null) {
            blankMoods
        } else {
            blankMoods.plus(selectedMood.mood to selectedMood)
        }
    }

    private val _uiState = MutableStateFlow(CreateEchoUiState())
    val uiState = combine(
        _uiState,
        _moods, _selectedMood) { uiState, moods, selectedMood ->
        uiState.copy(
            moods = moods.values.toList(),
            selectedMood = selectedMood,

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
                _topicsSearchText.update { action.topic }
            }

            is CreateEchoAction.ShowHideTopics -> {
                println("SHOW HIDE TOPICS CALLED: VISIBLE = ${action.visible}")
                topicsVisible.update {
                    action.visible
                }
            }
            is CreateEchoAction.SelectTopic -> {
                _topicsSearchText.update {
                    action.topic
                }
                selectedTopics.update {
                    it.plus(action.topic)
                }
            }
            is CreateEchoAction.CreateTopic -> {
                viewModelScope.launch {
                    topicRepo.create(action.topic)
                }
            }
            is CreateEchoAction.RemoveTopic -> {
                selectedTopics.update {
                    it.minus(action.topic)
                }
            }

            is CreateEchoAction.ShowHideMoods -> {
                _uiState.update {
                    it.copy(
                        isSelectMoodExpanded = action.visible
                    )
                }
            }
            is CreateEchoAction.SelectMood -> {
                val selectedMood =
                    if (_selectedMood.value == action.mood){
                        null
                    }else coloredMoods[action.mood.mood]

                _selectedMood.update {
                    selectedMood
                }
            }
            is CreateEchoAction.ConfirmMood -> {
                _uiState.update {
                    it.copy(
                        confirmedMood = action.mood
                    )
                }
            }
        }
    }
}