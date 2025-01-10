package nl.codingwithlinda.echojournal.feature_record.data

import android.annotation.SuppressLint
import android.app.Application
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.di.DispatcherProvider
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorderData
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream


@SuppressLint("MissingPermission")
class AndroidAudioRecorder(
    private val context: Application,
    private val dispatcherProvider: DispatcherProvider
): AudioRecorder {

    val RECORDER_BPP: Int = 16
    val AUDIO_RECORDER_FILE_EXT_WAV: String = ".wav"
    val AUDIO_RECORDER_FOLDER: String = "AudioRecorder"
    val AUDIO_RECORDER_TEMP_FILE: String = "record_temp.raw"
    val RECORDER_SAMPLERATE: Int = 44100
    val RECORDER_CHANNELS: Int = AudioFormat.CHANNEL_IN_STEREO
    val RECORDER_AUDIO_ENCODING: Int = AudioFormat.ENCODING_PCM_16BIT

    // Get the minimum buffer size required for the successful creation of an AudioRecord object.
    val bufferSizeInBytes = AudioRecord.getMinBufferSize(
        RECORDER_SAMPLERATE,
        RECORDER_CHANNELS,
        RECORDER_AUDIO_ENCODING
    );
    // Initialize Audio Recorder.


    val audioRecorder = AudioRecord(
        MediaRecorder.AudioSource.MIC,
        RECORDER_SAMPLERATE,
        RECORDER_CHANNELS,
        RECORDER_AUDIO_ENCODING,
        bufferSizeInBytes
    )

    val numberOfReadBytes   = 0;
    val audioBuffer     =  ByteArray(bufferSizeInBytes)
    val recording       = false;
    val tempFloatBuffer = FloatArray(3);
    val tempIndex           = 0;
    val totalReadBytes      = 0;
    val totalByteBuffer = ByteArray(60 * 44100 * 2)

    private val uri = File(context.filesDir, "echo.mp3")
    private val _listener = MutableStateFlow(AudioRecorderData(
        duration = 0L,
        uri = uri.path,
        amplitudesUri = ""
    ))
    override val listener: StateFlow<AudioRecorderData>
        get() = _listener.asStateFlow()

    override fun start() {
        val bufferSize = AudioRecord.getMinBufferSize(
            RECORDER_SAMPLERATE,
            RECORDER_CHANNELS,
            RECORDER_AUDIO_ENCODING
        );

        CoroutineScope(dispatcherProvider.default).launch {
            audioRecorder.startRecording()

        }
        CoroutineScope(dispatcherProvider.default).launch {

            val inputStream = ByteArrayInputStream(audioBuffer)
            val out = FileOutputStream(uri)
            var read = 0
            var bytes = ByteArray(bufferSize)
            while (inputStream.read(bytes) != -1){
                read = audioRecorder.read(audioBuffer, 0, bufferSize)
                out.write(audioBuffer)
            }
        }
    }

    override fun pause() {

    }

    override fun stop() {
        audioRecorder.stop()
        audioRecorder.release()

    }
}