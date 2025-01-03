package nl.codingwithlinda.echojournal.feature_entries.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import nl.codingwithlinda.echojournal.feature_entries.domain.usecase.FilterOnMoodAndTopic
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.entries
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeUiTopics
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.testSound
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.testSound2
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.EchoesUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.FilterEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.MoodsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.GroupByTimestamp
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.limitTopics
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.moodToColorMap

class EchosViewModel(
    private val echoPlayer: EchoPlayer
): ViewModel() {

    private val filterOnMoodAndTopic = FilterOnMoodAndTopic()

    private val fakeTopics = fakeUiTopics()
    private val _topics = MutableStateFlow<List<UiTopic>>(fakeTopics)
    private val _selectedTopics = MutableStateFlow<List<UiTopic>>(emptyList())
    private val _topicsUiState = MutableStateFlow(TopicsUiState(
        topics = fakeTopics
    ))
    val topicsUiState = combine(_topics, _selectedTopics, _topicsUiState){topics, selectedTopics, topicsUiState ->

        topicsUiState.copy(
            topics = topics,
            selectedTopics = selectedTopics,
            selectedTopicsUiText = limitTopics(selectedTopics),
            shouldShowClearSelection = selectedTopics.isNotEmpty()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _topicsUiState.value)


    private val _selectedMoods = MutableStateFlow<List<UiMood>>(emptyList())
    private val _moodsUiState = MutableStateFlow(
        MoodsUiState(
            moods = moodToColorMap.entries.sortedBy { it.key }.map { it.value },
            selectedMoods = emptyList()
        )
    )
    val moodsUiState = combine(_selectedMoods, _moodsUiState){selectedMoods, moodsUiState ->
        moodsUiState.copy(
            selectedMoods = selectedMoods,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _moodsUiState.value)

    private val _echoes = MutableStateFlow(entries)
    val echoesUiState
            = combine(_echoes, _selectedTopics, _selectedMoods) { echoes, topics, moods ->

        val moodNames = moods.map { it.mood }
        val topicNames = topics.map { it.name }

        val selectedEchoes =  filterOnMoodAndTopic.filter(echoes, moodNames, topicNames)
        val res = GroupByTimestamp.createGroups(selectedEchoes)

        EchoesUiState(
            echoesTotal = echoes.size,
            selectedEchoes = res
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EchoesUiState(0, emptyList()))



    fun onFilterAction(action: FilterEchoAction){
        when(action){
            is FilterEchoAction.ToggleSelectTopic -> {
               if (action.topic in _selectedTopics.value){
                   _selectedTopics.update {
                       it.minus(action.topic)
                   }
               }else{
                   _selectedTopics.update {
                       it.plus(action.topic)
                   }
               }
            }

            FilterEchoAction.ClearTopicSelection -> {
               _selectedTopics.update { emptyList() }
            }

            FilterEchoAction.ClearMoodSelection -> {
                _selectedMoods.update {
                    emptyList()
                }
            }
            is FilterEchoAction.ToggleSelectMood -> {
                if (action.mood in _selectedMoods.value){
                    _selectedMoods.update {
                        it.minus(action.mood)
                    }
                }else {
                    _selectedMoods.update {
                        it.plus(action.mood)
                    }
                }
            }
        }
    }

    fun onReplayAction(action: ReplayEchoAction){
        when(action){
            ReplayEchoAction.Pause -> {
                echoPlayer.pause()
            }
            is ReplayEchoAction.Play -> {
                val id = action.echoId.toInt()
                val sound = if (id  % 2 == 0) testSound() else testSound2()
                echoPlayer.play(sound)
            }
            ReplayEchoAction.Stop -> {
                echoPlayer.stop()
            }
        }
    }
}