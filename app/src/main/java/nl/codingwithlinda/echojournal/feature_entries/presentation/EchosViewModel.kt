package nl.codingwithlinda.echojournal.feature_entries.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.entries
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeGroups
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeUiTopics
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.testSound
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.testSound2
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


    private val _selectedMoods = MutableStateFlow<List<UiMood>>(moodToColorMap.values.toList())
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
    val echoes = combine(_echoes, _topics, _selectedMoods) { echoes, topics, moods ->

        val moodNames = moods.map { it.mood }
       val selectedEchoes =  echoes.filter{
                it.mood in moodNames

        }
        GroupByTimestamp.createGroups(selectedEchoes)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())



    fun onFilterAction(action: FilterEchoAction){
        when(action){
            is FilterEchoAction.ToggleSelectTopic -> {
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
                }else
                    _selectedMoods.update {
                        it.plus(action.mood)
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