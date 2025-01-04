package nl.codingwithlinda.echojournal.core.di

import android.app.Application
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.util.AndroidAudioRecorder

class AndroidAppModule(
    val context:Application
): AppModule {

    val dispatcherProvider = AndroidDispatcherProvider()
    override val audioRecorder: AudioRecorder
        get() = AndroidAudioRecorder(
            context, dispatcherProvider
        )
}