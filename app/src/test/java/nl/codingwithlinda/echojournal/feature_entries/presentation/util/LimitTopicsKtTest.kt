package nl.codingwithlinda.echojournal.feature_entries.presentation.util

import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeUiTopics
import org.junit.Assert.assertEquals
import org.junit.Test

class LimitTopicsKtTest{

    @Test
    fun `test limit topics string`(){
        val topics = fakeUiTopics()

       val res =  limitTopics(topics)

        assertEquals("Topic 0, Topic 1 +2", res)

    }
}