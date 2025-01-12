package nl.codingwithlinda.echojournal.core.di

import android.app.Application
import nl.codingwithlinda.echojournal.core.data.AndroidEchoPlayer
import nl.codingwithlinda.echojournal.core.data.EchoFactory
import nl.codingwithlinda.echojournal.core.data.data_source.EchoAccess
import nl.codingwithlinda.echojournal.core.data.data_source.TopicsAccess
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import nl.codingwithlinda.echojournal.feature_record.data.AndroidMediaRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder

class AndroidAppModule(
    private val context:Application
): AppModule {

    private val dispatcherProvider = AndroidDispatcherProvider()

    override val audioRecorder: AudioRecorder
        get() = AndroidMediaRecorder(
            context = context,
            dispatcherProvider = dispatcherProvider,
        )

    override val echoPlayer: EchoPlayer
        get() = AndroidEchoPlayer(
            context = context,
            dispatcherProvider = dispatcherProvider,
        )

    override val topicsAccess: TopicsAccess = TopicsAccess()

    override val echoAccess: EchoAccess = EchoAccess()

    override val echoFactory: EchoFactory
        get() = EchoFactory(
            context
        )

}