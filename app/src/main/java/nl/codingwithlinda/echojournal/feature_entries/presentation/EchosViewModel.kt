package nl.codingwithlinda.echojournal.feature_entries.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
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

    private val _playingEchoUri = MutableStateFlow<String?>(null)
    private val _playbackState = echoPlayer.playbackState

    val replayUiState = combine(_echoes, _playingEchoUri, _playbackState){ echoes, uri , playbackState ->
        println("Echos viewmodel. playing uri: $uri")
        val currentlyPlaying = echoes.find {
            it.uri == uri
        } ?.let {
            it.id to  ReplayUiState(
                playbackState = playbackState,
                playingEchoUri = uri,
                waves = emptyList()
            )
        }

        echoes.associate{
           it.id to  ReplayUiState(
                playbackState = PlaybackState.STOPPED,
                playingEchoUri = null,
                waves = emptyList()
            )
        }.let {map ->
            currentlyPlaying?.let {  map.plus(it) } ?: map
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())


    init {
        viewModelScope.launch {
            _echoes.first().forEachIndexed {index, echo ->
                if (echo.id.startsWith("FAKE")) {
                    val sound =
                        if (index % 2 == 0) testSound().toString() else testSound2().toString()
                    println("test sound: $sound")
                    println("uri from sound: ${Uri.parse(sound)}")
                    val update = echo.copy(
                        uri = sound,
                        amplitudes = echoPlayer.amplitudes(Uri.parse(sound))
                    )
                    echoAccess.update(update)
                }
            }
        }
    }

    val echoesUiState
            = combine(_echoes, _selectedTopics, _selectedMoods) { echoes, topics, moods->

        val moodNames = moods.map { it.mood }
        val topicNames = topics.map { it.name }

        val selectedEchoes =  filterOnMoodAndTopic.filter(echoes, moodNames, topicNames)
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
                when(_playbackState.value){
                    PlaybackState.PLAYING ->{
                        echoPlayer.pause()

                    }
                    PlaybackState.PAUSED -> {
                        playback(action.uri, true)
                    }
                    PlaybackState.STOPPED -> {
                        playback(action.uri, false)
                    }
                }
            }
        }
    }

    private fun playback(uri: String, isPaused: Boolean){
        viewModelScope.launch {

            if (isPaused){
                echoPlayer.resume()
            } else {
                _playingEchoUri.update {
                    uri
                }
                echoPlayer.play(Uri.parse(uri))
            }
        }
    }

    private fun visualiseAmplitudes(uri: String, amplitudes: List<Float>){
        viewModelScope.launch {
            println("visualiseAmplitudes: $amplitudes")
            val delayMillis = (10L)
            amplitudes.onEachIndexed { index, fl ->

            }
            delay(delayMillis)
        }
    }
}