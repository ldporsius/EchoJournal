package nl.codingwithlinda.echojournal.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.core.presentation.mappers.blankMoods
import nl.codingwithlinda.echojournal.core.presentation.mappers.coloredMoods
import nl.codingwithlinda.echojournal.core.presentation.mappers.toUi
import nl.codingwithlinda.echojournal.core.presentation.state.TopicAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_settings.presentation.state.SettingsAction

class SettingsViewModel: ViewModel() {

    private val _coloredMoods = coloredMoods

    private val _moods = MutableStateFlow<Map<Mood, UiMood>>(blankMoods)
    private val _selectedMood = MutableStateFlow<Mood?>(null)
    val moods = _moods.combine(_selectedMood){moods, selected ->
        if (selected == null) blankMoods
        else
            blankMoods.plus(selected to selected.toUi())

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _moods.value)


    private val _selectedTopic = MutableStateFlow<Topic?>(null)
    val selectedTopic = _selectedTopic.asStateFlow()

    fun handleAction(action: SettingsAction){
        when(action){
            is SettingsAction.SelectMoodAction -> {
               toggleSelectedMood(action.mood)
            }
        }
    }

    private fun toggleSelectedMood(mood: Mood){
        _selectedMood.update { selected ->
            if (mood == selected ) null else mood
        }
    }

    fun handleTopicAction(action: TopicAction){
        when(action){
            is TopicAction.CreateTopic -> {

            }
            is TopicAction.RemoveTopic -> {

            }
            is TopicAction.SelectTopic -> {

            }
            is TopicAction.ShowHideTopics -> {

            }
            is TopicAction.TopicChanged -> {

            }
        }
    }
}