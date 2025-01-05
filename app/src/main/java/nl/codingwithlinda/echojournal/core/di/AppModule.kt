package nl.codingwithlinda.echojournal.core.di

import nl.codingwithlinda.echojournal.core.domain.data_source.repo.TopicsAccess
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder

interface AppModule {

    val audioRecorder: AudioRecorder
    val topicsAccess: TopicsAccess
}