package nl.codingwithlinda.echojournal.feature_entries.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EchosScreen
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeGroups

@Composable
fun EchosRoot() {

   val echosViewModel = viewModel<EchosViewModel>()
   val topicsUiState = echosViewModel.topicsUiState
      .collectAsStateWithLifecycle()

   EchosScreen(
      entries = fakeGroups,
      topicsUiState = topicsUiState.value,
      onFilterAction =
        echosViewModel::onFilterAction

   )
}