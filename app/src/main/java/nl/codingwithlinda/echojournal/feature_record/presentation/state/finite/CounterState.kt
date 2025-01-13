package nl.codingwithlinda.echojournal.feature_record.presentation.state.finite

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordingState

class CounterState {

    private var recordingState: RecordingState = RecordingState.STOPPED

    private val _counter = MutableStateFlow(0)
    val counter: StateFlow<Int> = _counter.asStateFlow()

    suspend fun startCounting(recordingState: RecordingState) {
        this.recordingState = recordingState
        when(recordingState){
            RecordingState.RECORDING -> {
               simulateWeAreCounting()
            }
            RecordingState.PAUSED -> {
                //nothing
            }
            RecordingState.STOPPED -> {
                _counter.update { 0 }

            }
        }
    }

    private suspend fun simulateWeAreCounting(){
        while(recordingState == RecordingState.RECORDING) {
            _counter.update {
                it + 100
            }
            delay(200)
        }
    }


}