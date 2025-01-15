package nl.codingwithlinda.echojournal.feature_record.presentation.state.finite

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.echojournal.feature_record.domain.RecordingState

class Counter(
    private val recordingState: () -> RecordingState,
    private val result: (Long) -> Unit
) {

    private var counter = 0L

    suspend fun startCounting() {
        simulateWeAreCounting()
    }

    fun resetCounter(){
        counter = 0L
    }


    private suspend fun simulateWeAreCounting() {
        while (recordingState() == RecordingState.RECORDING) {
            counter += 100

            delay(100)

            result(counter)

        }
    }
}