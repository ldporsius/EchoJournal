package nl.codingwithlinda.echojournal.feature_record.util

import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder

class AndroidAudioRecorder: AudioRecorder {
    override fun start() {
        println("started recording")
    }

    override fun stop() {
      println("stopped recording")
    }
}