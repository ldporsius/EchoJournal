package nl.codingwithlinda.echojournal.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.core.model.Mood
import nl.codingwithlinda.core.model.Topic
import nl.codingwithlinda.echojournal.core.presentation.mappers.blankMoods
import nl.codingwithlinda.echojournal.core.presentation.mappers.coloredMoods
import nl.codingwithlinda.echojournal.core.presentation.mappers.toUi
import nl.codingwithlinda.echojournal.core.presentation.topics.state.TopicAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeTopics
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_settings.presentation.state.SettingsAction

class SettingsViewModel: ViewModel() {


    private val _moods = MutableStateFlow<Map<Mood, UiMood>>(blankMoods)
    private val _selectedMood = MutableStateFlow<Mood?>(null)
    val moods = _moods.combine(_selectedMood){moods, selected ->
        if (selected == null) blankMoods
        else
            blankMoods.plus(selected to selected.toUi())

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _moods.value)


    private val _topics = MutableStateFlow<List<Topic>>(fakeTopics)
    private val _selectedTopics = MutableStateFlow<List<Topic>>(emptyList())
    val selectedTopics = _selectedTopics.asStateFlow()
    private val _selectedTopic = MutableStateFlow<Topic?>(null)
    val selectedTopic = _selectedTopic.asStateFlow()
    private val _topicInput = MutableStateFlow("")
    val topicInput = _topicInput.asStateFlow()

    val shouldShowCreateTopic = _topicInput.combine(_topics){input, topics ->
        input.uppercase() !in topics.map { it.name.uppercase() } && input.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val filteredTopics = _topicInput.combine(_topics){input, topics ->
        topics.filter { it.name.uppercase().contains(input.uppercase()) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _topics.value)

    val shouldShowTopicList = MutableStateFlow<Boolean>(false)
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
                val newTopic = Topic(name = action.text)
                _topics.update {
                    it.plus(
                        newTopic
                    )
                }
                _selectedTopics.update {
                    it.plus(newTopic)
                }
                _topicInput.update { "" }
            }
            is TopicAction.RemoveTopic -> {
                _selectedTopics.update {
                    it.minus(action.topic)
                }
            }
            is TopicAction.SelectTopic -> {
                _selectedTopic.update {
                    action.topic
                }
                _selectedTopics.update {
                    it.plus(action.topic)
                }
            }
            is TopicAction.ShowHideTopics -> {
                shouldShowTopicList.update {
                    !it
                }
            }
            is TopicAction.TopicChanged -> {
                _topicInput.update {
                    action.topicText
                }
            }
        }
    }
}