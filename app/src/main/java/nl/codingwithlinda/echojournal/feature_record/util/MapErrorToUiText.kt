package nl.codingwithlinda.echojournal.feature_record.util

import nl.codingwithlinda.echojournal.core.presentation.util.UiText
import nl.codingwithlinda.echojournal.feature_record.domain.error.RecordingFailedError

fun RecordingFailedError.toUiText(): UiText =  UiText.DynamicString("Recording failed")
