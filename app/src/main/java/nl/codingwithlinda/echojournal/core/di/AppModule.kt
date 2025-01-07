package nl.codingwithlinda.echojournal.core.di

import nl.codingwithlinda.echojournal.core.data.EchoFactory
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import nl.codingwithlinda.echojournal.core.domain.data_source.repo.EchoAccess
import nl.codingwithlinda.echojournal.core.domain.data_source.repo.TopicsAccess
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder

interface AppModule {

    val audioRecorder: AudioRecorder
    val echoPlayer: EchoPlayer
    val topicsAccess: TopicsAccess
    val echoAccess: EchoAccess
    val echoFactory: EchoFactory

}