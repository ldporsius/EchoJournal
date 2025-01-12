package nl.codingwithlinda.echojournal.feature_entries.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import nl.codingwithlinda.echojournal.core.domain.data_source.repo.EchoAccess
import nl.codingwithlinda.echojournal.core.domain.data_source.repo.TopicsAccess
import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.feature_create.presentation.state.PlaybackState
import nl.codingwithlinda.echojournal.feature_entries.domain.usecase.FilterOnMoodAndTopic
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.testSound
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.testSound2
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.EchoesUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.FilterEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.MoodsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.GroupByTimestamp
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.limitTopics
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.moodToColorMap
import nl.codingwithlinda.echojournal.feature_record.data.AndroidMediaRecorder

class EchosViewModel(
    private val echoAccess: EchoAccess,
    private val topicsAccess: TopicsAccess,
    private val echoPlayer: EchoPlayer
): ViewModel() {

    private val filterOnMoodAndTopic = FilterOnMoodAndTopic()

    private val _topics = topicsAccess.readAll()
    private val _selectedTopics = MutableStateFlow<List<Topic>>(emptyList())
    private val _topicsUiState = MutableStateFlow(TopicsUiState(
        topics = emptyList()
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

    private val _echoes = echoAccess.readAll()

    private val _playingEchoId = MutableSharedFlow<String>(replay = 10)
    private val _playbackState = echoPlayer.playbackState

    private val _replayUiState = MutableStateFlow<Pair<String, ReplayUiState>?>(null)
    val replayUiState =
        _echoes.combine(_replayUiState) { echoes, state ->

            val map = echoes.associate{
                it.id to  ReplayUiState(
                    playbackState = PlaybackState.STOPPED,
                    mood = it.mood,
                    waves = emptyList()
                )
            }

            if (state != null){
                map.plus(state)
            } else {
                map
            }

        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())


    init {

        _playingEchoId.onEach { id ->
            val echo = _echoes.first().find {
                it.id == id
            }
            echo?.let{ echo1 ->
                println("visualising echo with id: $id")
                _replayUiState.update {
                    id to ReplayUiState(
                        playbackState = PlaybackState.PLAYING,
                        mood = echo.mood,
                        waves = emptyList()
                    )
                }
                val uri = echo1.uri

                println("EchoViewModel is going to play uri: $uri")
                echoPlayer.play(uri)
                echoPlayer.visualiseAmplitudes(echo1.amplitudes, echo1.duration)
            }

        }.launchIn(viewModelScope)

        combine(echoPlayer.waves, echoPlayer.playbackState){ waves, playbackState ->
            _replayUiState.update {
                it?.copy(
                    second = it.second.copy(
                        playbackState = playbackState,
                        waves = waves
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    val echoesUiState
            = combine(_echoes, _selectedTopics, _selectedMoods) { echoes, topics, moods->

        val moodNames = moods.map { it.mood }

        val selectedEchoes =  filterOnMoodAndTopic.filter(echoes, moodNames, topics)
        val res = GroupByTimestamp.createGroups(selectedEchoes)

        EchoesUiState(
            echoesTotal = echoes.size,
            selectedEchoes = res,
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
            is ReplayEchoAction.Play -> {
                println("ECHOES VIEWMODEL. PLAYBACKSTATE = ${_playbackState.value}")
                viewModelScope.launch {

                    when(_playbackState.value){
                        PlaybackState.PLAYING ->{
                            echoPlayer.pause()
                        }
                        PlaybackState.PAUSED -> {
                            _playingEchoId.emit(
                                action.id
                            )
                        }
                        PlaybackState.STOPPED -> {
                            _playingEchoId.emit(
                                action.id
                            )
                        }
                    }
                }
            }
        }
    }

}