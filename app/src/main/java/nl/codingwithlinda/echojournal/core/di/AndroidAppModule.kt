package nl.codingwithlinda.echojournal.core.di

import android.app.Application
import nl.codingwithlinda.echojournal.core.data.AndroidEchoPlayer
import nl.codingwithlinda.echojournal.core.data.EchoFactory
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import nl.codingwithlinda.echojournal.feature_record.data.AndroidMediaRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.persistence.data.repository.EchoRepo
import nl.codingwithlinda.persistence.data.repository.TopicRepo

class AndroidAppModule(
    private val context:Application
): AppModule {

    override val dispatcherProvider = AndroidDispatcherProvider()

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

    override val topicsAccess = TopicRepo(context)

    override val echoAccess = EchoRepo(context)

    override val echoFactory: EchoFactory
        get() = EchoFactory(
            context
        )

}