package nl.codingwithlinda.echojournal.feature_create.data.repo

import nl.codingwithlinda.echojournal.core.data.TopicFactory
import nl.codingwithlinda.echojournal.core.domain.data_source.repo.TopicsAccess
import org.junit.Assert.*
import org.junit.Test
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nl.codingwithlinda.echojournal.test_dispatchers.TestDispatcherProvider
import org.junit.After
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
class TopicRepoTest{
    val dispatcherProvider = TestDispatcherProvider()
    val topicsAccess = TopicsAccess()
    val topicFactory = TopicFactory()
    val repo = TopicRepo(
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

        repo.applyFilter.test {

            repo.searchText.update {
                "a"
            }
            val em0 = awaitItem()

            println("em0 = $em0")

            repo.searchText.update {
                "z"
            }
            advanceUntilIdle()
            val em1 = awaitItem()

            println("em1 = $em1")

            cancelAndConsumeRemainingEvents().also {
                it.onEach {
                    println("em2 = $it")

                }
            }
        }
    }

}