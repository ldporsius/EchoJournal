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
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.moodToColorMap

class CreateEchoViewModel(
    val topicRepo: TopicRepo
): ViewModel() {

    private val topicsSearchText = MutableStateFlow("")
    private val _allTopics = topicRepo.readAll()
    private val filteredTopics = _allTopics.combine(topicsSearchText) { topics , search ->
        topics.filter { it.contains(search, ignoreCase = true) }
    }

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
    val uiState = combine( _uiState, filteredTopics, topicsSearchText, _moods) { uiState, filteredTopics, search , moods ->
        uiState.copy(
            topic = search,
            topics = filteredTopics,
            moods = moods.values.toList()
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
                _uiState.update {
                    it.copy(
                        selectedMood = selectedMood,
                    )
                }
            }
        }
    }
}