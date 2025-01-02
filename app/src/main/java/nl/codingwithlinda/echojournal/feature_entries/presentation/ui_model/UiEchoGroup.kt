package nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model

import nl.codingwithlinda.echojournal.core.presentation.util.UiText

data class UiEchoGroup(
    val header: UiText,
    val entries: List<UiEcho>

)
