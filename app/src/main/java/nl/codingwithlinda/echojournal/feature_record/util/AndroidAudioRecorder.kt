package nl.codingwithlinda.echojournal.feature_record.util

import android.app.Application
import android.content.Intent
import android.media.MediaRecorder
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.core.content.PackageManagerCompat.LOG_TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.di.DispatcherProvider
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorderData
import java.io.File
import java.io.IOException

class AndroidAudioRecorder(
    private val context: Application,
    private val dispatcherProvider: DispatcherProvider
): AudioRecorder, RecognitionListener {
    private var fileName: String = File(context.filesDir,"audio.3gp").path

    private val _listener = MutableStateFlow(AudioRecorderData(
        duration = 0L,
        uri = fileName
    ))
    override val listener: StateFlow<AudioRecorderData>
        get() = _listener.asStateFlow()

    private val recognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

    private var recorder: MediaRecorder? = null

    private var isRecording: Boolean = false

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
               e.printStackTrace()
            }
            start()
        }

        isRecording = true

    }

    private fun stopRecording() {

        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    private fun startSpeechRecognition() {

        val isSpeechRecognitionAvailable = SpeechRecognizer.isRecognitionAvailable(context)
        if (!isSpeechRecognitionAvailable) {
            println("Speech recognition is not available.")
            return
        }

        val intent = Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        ).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE, "en-US"
            )
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
        }

        recognizer.setRecognitionListener(this)

        recognizer.startListening(intent)
    }
    override fun start() {
        startSpeechRecognition()
        println("started recording")
        startRecording()
    }

    override fun stop() {
      println("stopped recording")

        isRecording = false
        stopRecording()
        recognizer.stopListening()
    }

    override fun onReadyForSpeech(p0: Bundle?) {
        println("READY FOR SPEECH")

    }

    override fun onBeginningOfSpeech() {
        println("BEGINNING OF SPEECH")
    }

    override fun onRmsChanged(rmsDb: Float) {
       println("RMS CHANGED $rmsDb")
       /* _listener.update {
            it.copy(
                amplitude = rmsDb * ( 1f / (12f - (-2f)))
            )
        }*/
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

    override fun onResults(result: Bundle?) {
       println("RESULTS $result")
        result
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let{
                println("RESULTS text =  $it")
            }
    }

    override fun onPartialResults(p0: Bundle?) {
        println("PARTIAL RESULTS $p0")
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
        println("EVENT $p0 $p1")
    }
}