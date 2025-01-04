package nl.codingwithlinda.echojournal.feature_record.presentation.preview

import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioUiState

fun fakeRecordAudioUiState(
    isRecording: Boolean
) = RecordAudioUiState(
    isRecording = isRecording,
    duration = "00:00"
)