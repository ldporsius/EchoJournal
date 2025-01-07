package nl.codingwithlinda.echojournal.feature_entries.presentation.state

import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEchoGroup

data class EchoesUiState(
    val echoesTotal:Int,
    val selectedEchoes: List<UiEchoGroup>,
){
}
