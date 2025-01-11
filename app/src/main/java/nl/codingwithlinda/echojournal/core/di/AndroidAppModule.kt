package nl.codingwithlinda.echojournal.core.di

import android.app.Application
import nl.codingwithlinda.echojournal.core.data.AndroidEchoPlayer
import nl.codingwithlinda.echojournal.core.data.AudioExtractorAMR
import nl.codingwithlinda.echojournal.core.data.AudioSampleExtractor
import nl.codingwithlinda.echojournal.core.data.EchoFactory
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import nl.codingwithlinda.echojournal.core.domain.SoundCapturer
import nl.codingwithlinda.echojournal.core.domain.data_source.repo.EchoAccess
import nl.codingwithlinda.echojournal.core.domain.data_source.repo.TopicsAccess
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.data.AndroidMediaRecorder

class AndroidAppModule(
    private val context:Application
): AppModule {

    private val dispatcherProvider = AndroidDispatcherProvider()
    private val soundCapturer = SoundCapturer()
    override val audioRecorder: AudioRecorder
        get() = AndroidMediaRecorder(
            context = context,
            dispatcherProvider = dispatcherProvider,
        )

    override val echoPlayer: EchoPlayer
        get() = AndroidEchoPlayer(
            context = context,
            dispatcherProvider = dispatcherProvider,
            soundCapturer = soundCapturer
        )

    override val topicsAccess: TopicsAccess = TopicsAccess()

    override val echoAccess: EchoAccess = EchoAccess()

    override val echoFactory: EchoFactory
        get() = EchoFactory(
            context
        )

}