package nl.codingwithlinda.echojournal.core.data

import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import java.io.IOException
import java.nio.ByteBuffer

class AudioExtractorAMR {

    companion object {
        private const val TIMEOUT_US = 10000L
        private const val AMR_FRAME_SIZE = 32 // AMR-NB frame size
        private const val PCM_BUFFER_SIZE = 8192
    }

    private val samples = mutableListOf<Float>()

    @Throws(IOException::class)
    fun extractSamples(audioPath: String): FloatArray {
        val extractor = MediaExtractor().apply {
            setDataSource(audioPath)
        }

        val format = (0 until extractor.trackCount)
            .map { extractor.getTrackFormat(it) }
            .firstOrNull {
                val mime = it.getString(MediaFormat.KEY_MIME)
                mime == "audio/3gpp" || mime?.startsWith("audio/") == true
            }?.also { extractor.selectTrack(0) }
            ?: throw IOException("No audio track found")

        // Configure output format for raw PCM
        val outputFormat = MediaFormat.createAudioFormat(
            MediaFormat.MIMETYPE_AUDIO_RAW,
            format.getInteger(MediaFormat.KEY_SAMPLE_RATE),
            format.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
        ).apply {
            setInteger(MediaFormat.KEY_PCM_ENCODING, 16) // 16-bit PCM
        }

        val decoder = MediaCodec.createDecoderByType(
            format.getString(MediaFormat.KEY_MIME) ?: throw IOException("No MIME type found")
        ).apply {
            configure(outputFormat, null, null, 0)
            start()
        }

        try {
            decodeSamples(extractor, decoder, format)
        } finally {
            cleanup(decoder, extractor)
        }

        return samples.toFloatArray()
    }

    private fun decodeSamples(
        extractor: MediaExtractor,
        decoder: MediaCodec,
        format: MediaFormat
    ) {
        val bufferInfo = MediaCodec.BufferInfo()
        var sawInputEOS = false
        var sawOutputEOS = false

        val isAmr = format.getString(MediaFormat.KEY_MIME) == "audio/3gpp"
        val inputSize = if (isAmr) AMR_FRAME_SIZE else PCM_BUFFER_SIZE

        while (!sawOutputEOS) {
            if (!sawInputEOS) {
                val inputBufferIndex = decoder.dequeueInputBuffer(TIMEOUT_US)
                if (inputBufferIndex >= 0) {
                    val inputBuffer = decoder.getInputBuffer(inputBufferIndex) ?: continue
                    inputBuffer.clear()

                    val sampleSize = extractor.readSampleData(inputBuffer, 0)

                    when {
                        sampleSize < 0 -> {
                            decoder.queueInputBuffer(
                                inputBufferIndex, 0, 0, 0,
                                MediaCodec.BUFFER_FLAG_END_OF_STREAM
                            )
                            sawInputEOS = true
                        }
                        else -> {
                            decoder.queueInputBuffer(
                                inputBufferIndex, 0, sampleSize,
                                extractor.sampleTime, 0
                            )
                            extractor.advance()
                        }
                    }
                }
            }

            when (val outputBufferIndex = decoder.dequeueOutputBuffer(bufferInfo, TIMEOUT_US)) {
                MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                    // Format changed to PCM
                }
                MediaCodec.INFO_TRY_AGAIN_LATER -> {
                    // No output available yet
                }
                else -> if (outputBufferIndex >= 0) {
                    val outputBuffer = decoder.getOutputBuffer(outputBufferIndex)
                    outputBuffer?.let {
                        processOutputBuffer(
                            it,
                            bufferInfo.size,
                            format.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
                        )
                    }

                    decoder.releaseOutputBuffer(outputBufferIndex, false)
                    if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) {
                        sawOutputEOS = true
                    }
                }
            }
        }
    }

    private fun processOutputBuffer(
        outputBuffer: ByteBuffer,
        size: Int,
        channelCount: Int
    ) {
        val data = ByteArray(size)
        outputBuffer.get(data)

        // Process 16-bit PCM samples
        for (i in 0 until size step (2 * channelCount)) {
            val sample = (data[i + 1].toInt() shl 8 or (data[i].toInt() and 0xFF)).toShort()
            val normalizedSample = sample / 32768f
            samples.add(normalizedSample)
        }
    }

    private fun cleanup(decoder: MediaCodec, extractor: MediaExtractor) {
        decoder.apply {
            stop()
            release()
        }
        extractor.release()
    }
}
