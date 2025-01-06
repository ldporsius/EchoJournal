package nl.codingwithlinda.echojournal.test_dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import nl.codingwithlinda.echojournal.core.di.DispatcherProvider

class TestDispatcherProvider @OptIn(ExperimentalCoroutinesApi::class) constructor(
    val dispatcher: CoroutineDispatcher = kotlinx.coroutines.test.UnconfinedTestDispatcher()
): DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = dispatcher
    override val mainImmediate: CoroutineDispatcher
        get() = dispatcher
    override val io: CoroutineDispatcher
        get() = dispatcher
    override val default: CoroutineDispatcher
        get() = dispatcher
}