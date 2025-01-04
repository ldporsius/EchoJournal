package nl.codingwithlinda.echojournal.feature_record.util

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorderData

class AndroidAudioRecorder(
    context: Application
): AudioRecorder, RecognitionListener {

    private val _listener = MutableStateFlow(AudioRecorderData(
        amplitude = 0f
    ))
    override val listener: StateFlow<AudioRecorderData>
        get() = _listener.asStateFlow()

    val recognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

    override fun start() {
        println("started recording")
        recognizer.setRecognitionListener(this)
        recognizer.startListening(Intent())
    }

    override fun stop() {
      println("stopped recording")
    }

    override fun onReadyForSpeech(p0: Bundle?) {
        println("READY FOR SPEECH")
    }

    override fun onBeginningOfSpeech() {
        println("BEGGINING OF SPEECH")
    }

    override fun onRmsChanged(rms: Float) {
       println("RMS CHANGED $rms")
    }

    override fun onBufferReceived(p0: ByteArray?) {
        println("BUFFER RECEIVED $p0")
    }

    override fun onEndOfSpeech() {
        println("END OF SPEECH")
    }

    override fun onError(p0: Int) {
        println("ERROR $p0")
    }

    override fun onResults(p0: Bundle?) {
       println("RESULTS $p0")
    }

    override fun onPartialResults(p0: Bundle?) {
        println("PARTIAL RESULTS $p0")
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
        println("EVENT $p0 $p1")
    }
}