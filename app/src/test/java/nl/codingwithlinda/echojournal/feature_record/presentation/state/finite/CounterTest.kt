package nl.codingwithlinda.echojournal.feature_record.presentation.state.finite

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import nl.codingwithlinda.echojournal.feature_record.domain.RecordingState
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.system.measureTimeMillis

class CounterTest{

    private val recordingState = MutableStateFlow(RecordingState.STOPPED)
    private var counterResult = 0L
    private val counter = Counter(
        recordingState = {
            recordingState.value
        },
        result = {
           counterResult = it
            println("result: $it")
        }
    )
    @Before
    fun setup(){
        counterResult = 0
    }

    @Test
    fun `count to ten`(): Unit = runBlocking{

        recordingState.update {
            RecordingState.RECORDING
        }
        launch {
            counter.startCounting()
        }

        while(counterResult < 10_000){
            delay(100)
        }
        recordingState.update {
            RecordingState.STOPPED
        }
        assertEquals(10_000, counterResult)
    }

    @Test
    fun `counter paused and resumed gives the duration without the pause`(): Unit = runBlocking{
        withTimeout(4100){

            recordingState.update {
                RecordingState.RECORDING
            }
            launch {
                counter.startCounting()
            }
            delay(1000)

            recordingState.update {
                RecordingState.PAUSED
            }

            println("paused counter at ${counterResult}")
            delay(1000)

            recordingState.update {
                RecordingState.RECORDING
            }
            launch {
                counter.startCounting()
            }
           println("resumed counter at ${counterResult}")

            delay(1000)

            recordingState.update {
                RecordingState.STOPPED
            }

            delay(100)
            assertEquals(2000, counterResult)
        }
    }
}