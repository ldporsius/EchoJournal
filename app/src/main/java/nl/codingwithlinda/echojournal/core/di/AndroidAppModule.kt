package nl.codingwithlinda.echojournal.core.di

import android.app.Application
import nl.codingwithlinda.echojournal.core.data.AndroidEchoPlayer
import nl.codingwithlinda.echojournal.core.data.AudioExtractorAMR
import nl.codingwithlinda.echojournal.core.data.AudioSampleExtractor
import nl.codingwithlinda.echojournal.core.data.EchoFactory
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import nl.codingwithlinda.echojournal.core.domain.data_source.repo.EchoAccess
import nl.codingwithlinda.echojournal.core.domain.data_source.repo.TopicsAccess
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.data.AndroidMediaRecorder

class AndroidAppModule(
    private val context:Application
): AppModule {

    private val dispatcherProvider = AndroidDispatcherProvider()

    override val audioRecorder: AudioRecorder
        get() = AndroidMediaRecorder(
            context, dispatcherProvider
        )

    override val echoPlayer: EchoPlayer
        get() = AndroidEchoPlayer(
            context,
            dispatcherProvider,
            audioSampleExtractor = AudioExtractorAMR()
        )

    override val topicsAccess: TopicsAccess
        get() = TopicsAccess()

    override val echoAccess: EchoAccess
        get() = EchoAccess()

    override val echoFactory: EchoFactory
        get() = EchoFactory()

}