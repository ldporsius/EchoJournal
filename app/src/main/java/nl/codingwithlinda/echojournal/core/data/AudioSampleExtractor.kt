package nl.codingwithlinda.echojournal.core.data

import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import java.io.IOException
import java.nio.ByteBuffer

class AudioSampleExtractor {

        companion object {
            private const val TIMEOUT_US = 10000L
        }

        private val samples = mutableListOf<Float>()

        @Throws(IOException::class)
        fun extractSamples(audioPath: String): FloatArray {
            val extractor = MediaExtractor().apply {
                setDataSource(audioPath)
            }

            // Select the first audio track
            val format = (0 until extractor.trackCount)
                .map {
                    val trackFormat = extractor.getTrackFormat(it)

                    println("AudioSampleExtractor trackFormat: $trackFormat")

                    trackFormat
                }
                .firstOrNull {

                    println("AudioSampleExtractor it.getString(MediaFormat.KEY_MIME): ${it.getString(MediaFormat.KEY_MIME)}")

                    it.getString(MediaFormat.KEY_MIME)?.startsWith("audio/") == true }
                ?.also { extractor.selectTrack(0) }
                ?: throw IOException("No audio track found")

            // Create and configure decoder
            val decoder = MediaCodec.createDecoderByType(
                format.getString(MediaFormat.KEY_MIME) ?: throw IOException("No MIME type found")
            ).apply {
                configure(format, null, null, 0)
                start()
            }

            try {
                decodeSamples(extractor, decoder)
            } finally {
                cleanup(decoder, extractor)
            }

            return samples.toFloatArray()
        }

        private fun decodeSamples(extractor: MediaExtractor, decoder: MediaCodec) {
            val bufferInfo = MediaCodec.BufferInfo()
            var sawInputEOS = false
            var sawOutputEOS = false

            while (!sawOutputEOS) {
                // Handle input
                if (!sawInputEOS) {
                    val inputBufferIndex = decoder.dequeueInputBuffer(TIMEOUT_US)
                    if (inputBufferIndex >= 0) {
                        val inputBuffer = decoder.getInputBuffer(inputBufferIndex)
                        val sampleSize = inputBuffer?.let { extractor.readSampleData(it, 0) } ?: -1

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

                // Handle output
                when (val outputBufferIndex = decoder.dequeueOutputBuffer(bufferInfo, TIMEOUT_US)) {
                    in 0..Int.MAX_VALUE -> {
                        val outputBuffer = decoder.getOutputBuffer(outputBufferIndex)
                        outputBuffer?.let { processOutputBuffer(it, bufferInfo.size) }

                        decoder.releaseOutputBuffer(outputBufferIndex, false)
                        if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) {
                            sawOutputEOS = true
                        }
                    }
                }
            }
        }

        private fun processOutputBuffer(outputBuffer: ByteBuffer, size: Int) {
            val data = ByteArray(size)
            outputBuffer.get(data)

            // Convert bytes to float samples (assuming 16-bit PCM)
            for (i in 0 until data.size step 2) {
                val sample = (data[i + 1].toInt() shl 8 or (data[i].toInt() and 0xFF)).toShort()
                val normalizedSample = sample / 32768f  // Normalize to [-1, 1]
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