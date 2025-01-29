package nl.codingwithlinda.echojournal.core.di

import nl.codingwithlinda.core.model.Echo
import nl.codingwithlinda.core.model.Topic
import nl.codingwithlinda.echojournal.core.data.EchoFactory
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.persistence.domain.DataSourceAccess

interface AppModule {

    val dispatcherProvider: DispatcherProvider
    val audioRecorder: AudioRecorder
    val echoPlayer: EchoPlayer
    val topicsAccess: DataSourceAccess<Topic, String>
    val echoAccess: DataSourceAccess<Echo, String>
    val echoFactory: EchoFactory

}