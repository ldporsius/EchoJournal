package nl.codingwithlinda.echojournal.feature_create.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.data.EchoDto
import nl.codingwithlinda.echojournal.core.data.EchoFactory
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import nl.codingwithlinda.echojournal.core.data.data_source.EchoAccess
import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterDuration
import nl.codingwithlinda.echojournal.core.presentation.mappers.blankMoods
import nl.codingwithlinda.echojournal.core.presentation.mappers.coloredMoods
import nl.codingwithlinda.echojournal.core.presentation.state.TopicAction
import nl.codingwithlinda.echojournal.feature_create.data.repo.TopicRepo
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState
import nl.codingwithlinda.echojournal.feature_create.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood

class CreateEchoViewModel(
    private val echoDto: EchoDto,
    private val audioEchoPlayer: EchoPlayer,
    private val dateTimeFormatter: DateTimeFormatterDuration,
    private val topicRepo: TopicRepo,
    private val echoFactory: EchoFactory,
    private val echoAccess: EchoAccess,
    private val onSaved: () -> Unit
): ViewModel() {

    private val _topicsSearchText = MutableStateFlow("")

    private val _filteredTopics = _topicsSearchText.combine(topicRepo.readAll()){search , topics->
        if (search.isBlank()) topics

        else {
            topics.filter {
                it.name.contains(search, ignoreCase = true)
            }
        }
    }
    private val topicsVisible = MutableStateFlow(false)

    private val _selectedTopics = MutableStateFlow<Set<Topic>>(emptySet())

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

    private val _moods = _selectedMood.map {selectedMood ->
        if (selectedMood == null) {
            blankMoods
        } else {
            blankMoods.plus(selectedMood.mood to selectedMood)
        }
    }


    private val _uiState = MutableStateFlow(CreateEchoUiState(
        duration = dateTimeFormatter.formatDurationProgress(0f, echoDto.duration),
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


    private var amplitudes = emptyList<Float>()
    init {
        viewModelScope.launch {
           fetchAmplitudesFromTempFile()
        }

        observeAmplitudesPlayback()
    }

    private suspend fun fetchAmplitudesFromTempFile(){
        try {
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

    private fun observeAmplitudesPlayback(){
        audioEchoPlayer.waves.onEach { waves ->
            if (amplitudes.isEmpty()) return@onEach
            val progress = waves.size.toFloat() / amplitudes.size.toFloat()

            _uiState.update {
                it.copy(
                    amplitudesPlayed = waves,
                    duration = dateTimeFormatter.formatDurationProgress(progress , echoDto.duration)
                )
            }
        }.launchIn(viewModelScope)
    }
    fun onAction(action: CreateEchoAction) {
        when (action) {
            CreateEchoAction.PlayEcho -> {

               audioEchoPlayer.play(echoDto.uri)
                viewModelScope.launch {
                    audioEchoPlayer.visualiseAmplitudes(amplitudes, echoDto.duration)
                }
            }

            CreateEchoAction.PauseEcho -> {
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

                    val topics = _selectedTopics.value.toList()

                    echoFactory.createEcho(
                        echoDto = echoDto,
                        topics = topics,
                        title = userInput.title,
                        description = userInput.description,
                        mood = userInput.confirmedMood.mood,
                        amplitudes = amplitudes
                    ).also {
                        echoAccess.create(it).also {echo ->
                            echoFactory.persistEcho(
                                source = echoDto.uri,
                                target = echo.uri
                            )
                        }
                        onSaved()

                    }
                }

            }
        }
    }

    fun handleTopicAction(action: TopicAction){
        when(action){
            is TopicAction.CreateTopic -> {
                viewModelScope.launch {
                    topicRepo.create(action.text).also {topic ->
                        _selectedTopics.update {
                            it.plus(topic)
                        }
                        _topicsSearchText.update {
                            ""
                        }
                    }
                }
            }
            is TopicAction.RemoveTopic -> {
                _selectedTopics.update {
                    it.minus(action.topic)
                }
                _topicsSearchText.update {
                    ""
                }
            }
            is TopicAction.SelectTopic -> {
                _topicsSearchText.update {
                    ""
                }

                _selectedTopics.update {
                    it.plus(action.topic)
                }
            }
            is TopicAction.ShowHideTopics -> {
                topicsVisible.update {
                    action.visible
                }
            }
            is TopicAction.TopicChanged -> {
                _topicsSearchText.update { action.topic }
            }
        }
    }
}