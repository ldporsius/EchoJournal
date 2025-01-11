package nl.codingwithlinda.echojournal.feature_create.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.data.EchoDto
import nl.codingwithlinda.echojournal.core.data.EchoFactory
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import nl.codingwithlinda.echojournal.core.domain.data_source.repo.EchoAccess
import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.core.presentation.util.blankMoods
import nl.codingwithlinda.echojournal.feature_create.data.repo.TopicRepo
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState
import nl.codingwithlinda.echojournal.feature_create.presentation.state.PlaybackState
import nl.codingwithlinda.echojournal.feature_create.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.moodToColorMap

class CreateEchoViewModel(
    private val echoDto: EchoDto,
    private val audioEchoPlayer: EchoPlayer,
    val topicRepo: TopicRepo,
    private val echoFactory: EchoFactory,
    private val echoAccess: EchoAccess,
    private val onSaved: () -> Unit
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

    private val _selectedTopics = MutableStateFlow<Set<String>>(emptySet())

    val topicsUiState = combine(_filteredTopics, _topicsSearchText, topicsVisible) { topics, search , visible ->
        TopicsUiState(
            topics = topics,
            searchText = search,
            isExpanded = visible
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TopicsUiState())

    val selectedTopics = _selectedTopics.map {
        it.toList()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedMood = MutableStateFlow<UiMood?>(null)
    private val coloredMoods = moodToColorMap
    private val _moods = _selectedMood.map {selectedMood ->
        if (selectedMood == null) {
            blankMoods
        } else {
            blankMoods.plus(selectedMood.mood to selectedMood)
        }
    }


    private val _uiState = MutableStateFlow(CreateEchoUiState(
        duration = echoDto.duration.toString(),
        amplitudes = emptyList()
    ))
    val uiState = combine(
        _uiState,
        _moods, _selectedMood) { uiState, moods, selectedMood ->
        uiState.copy(
            moods = moods.values.toList(),
            selectedMood = selectedMood,

        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


    private val playbackState = MutableStateFlow(PlaybackState.STOPPED)

    private var amplitudes = emptyList<Float>()
    init {
        viewModelScope.launch {
            try {
                println("uri in create echo viewmodel: ${Uri.parse(echoDto.uri.substringAfterLast('/'))}")

                val amplitudesPath = echoDto.amplitudesUri.substringAfterLast('/')
                val amplitudes = audioEchoPlayer.amplitudes(amplitudesPath)
                this@CreateEchoViewModel.amplitudes = amplitudes

                _uiState.update {
                    it.copy(
                        amplitudes = amplitudes
                    )
                }

            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun visualiseAmplitudes(amplitudes: List<Float>, duration: Long){
        viewModelScope.launch {

            if(amplitudes.isEmpty()) return@launch

            val amplitudesSilent = amplitudes.takeWhile {
                it < 1.0f
            }.size
            println("CreateEchoViewModel delaying while silent for $amplitudesSilent millis")

            delay(amplitudesSilent * 1L)
            val delayMillis = duration / amplitudes.size

            println("CreateEchoViewModel amplitudes size = ${amplitudes.size}")
            println("CreateEchoViewModel duration = ${duration}")
            println("CreateEchoViewModel delaying visualise by $delayMillis millis")

            (1 .. amplitudes.size).onEachIndexed { index, fl ->
                if (playbackState.value == PlaybackState.PLAYING) {
                    _uiState.update {
                        it.copy(
                            amplitudesPlayed = it.amplitudesPlayed + index
                        )
                    }
                    delay(delayMillis)
                }
            }

            _uiState.update {
                it.copy(
                    amplitudesPlayed = emptyList()
                )
            }
            playbackState.update {
                PlaybackState.STOPPED
            }
        }
    }

    fun onAction(action: CreateEchoAction) {
        when (action) {
            CreateEchoAction.PlayEcho -> {
                playbackState.update {
                    PlaybackState.PLAYING
                }
               audioEchoPlayer.play(Uri.parse(echoDto.uri))
               visualiseAmplitudes(amplitudes, echoDto.duration)

            }
            CreateEchoAction.PauseEcho -> {
                playbackState.update {
                    PlaybackState.PAUSED
                }
                audioEchoPlayer.pause()

            }
            is CreateEchoAction.TitleChanged -> {
                _uiState.update {
                    it.copy(
                        title = action.title
                    )
                }
            }

            is CreateEchoAction.DescriptionChanged -> {
                _uiState.update {
                    it.copy(
                        description = action.description
                    )
                }
            }

            is CreateEchoAction.TopicChanged -> {
                _topicsSearchText.update { action.topic }
            }

            is CreateEchoAction.ShowHideTopics -> {
                topicsVisible.update {
                    action.visible
                }
            }
            is CreateEchoAction.SelectTopic -> {
               _topicsSearchText.update {
                    ""
                }

                _selectedTopics.update {
                    it.plus(action.topic)
                }
            }
            is CreateEchoAction.CreateTopic -> {
                viewModelScope.launch {
                    topicRepo.create(action.topic).also {
                        _selectedTopics.update {
                            it.plus(action.topic)
                        }
                        _topicsSearchText.update {
                            ""
                        }
                    }
                }
            }
            is CreateEchoAction.RemoveTopic -> {
               _selectedTopics.update {
                    it.minus(action.topic)
                }
                _topicsSearchText.update {
                    ""
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

            CreateEchoAction.Save -> {
                viewModelScope.launch {
                    val userInput = uiState.value
                    userInput.confirmedMood ?: return@launch

                    val topics = topicsUiState.value.topics.map { Topic(it) }

                    echoFactory.createEcho(
                        echoDto = echoDto,
                        topics = topics,
                        title = userInput.title,
                        description = userInput.description,
                        mood = userInput.confirmedMood.mood,
                        amplitudes = amplitudes
                    ).also {
                        echoAccess.create(it)
                        onSaved()

                    }
                }

            }
        }
    }
}