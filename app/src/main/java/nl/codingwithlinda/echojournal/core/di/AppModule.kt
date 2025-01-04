package nl.codingwithlinda.echojournal.core.di

import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder

interface AppModule {

    val audioRecorder: AudioRecorder
}