package nl.codingwithlinda.echojournal.core.presentation.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test


class DateTimeFormatterDurationTest{
    val formatter = DateTimeFormatterDuration

    val expectedPattern = "\\d{2}:\\d{2}/\\d{2}:\\d{2}".toRegex()

    val progress = flow<Float> {
        (0 .. 10).forEach {
            emit(it /10f)
            delay(1000)
        }
    }
    @Test
    fun `test progress duration - returns string `() = runBlocking{

        progress.collect{
            val result = formatter.formatDurationProgress(it.toFloat(), 10_000L)
            println("result: $result")

            val isMatch = expectedPattern.matches(result)
            println("isMatch: $isMatch")

            assertTrue(isMatch)
        }



    }
}