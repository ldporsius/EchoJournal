package nl.codingwithlinda.echojournal.feature_create.data.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nl.codingwithlinda.echojournal.core.data.TopicFactory
import nl.codingwithlinda.echojournal.core.domain.data_source.repo.TopicsAccess
import nl.codingwithlinda.echojournal.test_dispatchers.TestDispatcherProvider
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TopicRepoTest{
    private val dispatcherProvider = TestDispatcherProvider()
    private val topicsAccess = TopicsAccess()
    private val topicFactory = TopicFactory()
    private val repo = TopicRepo(
        topicsAccess,
        topicFactory
    )

     @Before
    fun setup(){
        Dispatchers.setMain(dispatcherProvider.dispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `filter topic`(): Unit = runTest(dispatcherProvider.dispatcher) {
        repo.create("Apple")

        assertTrue(repo.readAll().first().contains("Apple"))
        repo.create("Banana")

        repo.readAll().first().onEach {
            println(it)
        }

    }

}