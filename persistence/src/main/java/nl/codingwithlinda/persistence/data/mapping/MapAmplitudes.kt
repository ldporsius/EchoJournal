package nl.codingwithlinda.persistence.data.mapping

import nl.codingwithlinda.persistence.model.AmplitudeEntity

fun amplitudeToEntity(echoId: String, amplitude: Float): AmplitudeEntity{
    return AmplitudeEntity(
        echoId = echoId,
        amplitude = amplitude
    )
}