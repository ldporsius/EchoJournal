package nl.codingwithlinda.echojournal.core.domain.util

sealed interface EchoResult<R, E: EchoError> {
    data class Success<R, E: EchoError>(val data: R) : EchoResult<R, E>
    data class Error<R, E: EchoError>(val error: E) : EchoResult<R, E>
}