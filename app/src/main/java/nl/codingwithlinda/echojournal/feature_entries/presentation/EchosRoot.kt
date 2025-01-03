package nl.codingwithlinda.echojournal.feature_entries.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EchosScreen

@Composable
fun EchosRoot(
    echoPlayer: EchoPlayer
) {

   val factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
         EchosViewModel(
           echoPlayer = echoPlayer
         )
      }
   }

   val echosViewModel = viewModel<EchosViewModel>(
      factory = factory
   )
   val topicsUiState = echosViewModel.topicsUiState
      .collectAsStateWithLifecycle()

   EchosScreen(
      echoesUiState = echosViewModel.echoesUiState.collectAsStateWithLifecycle().value,
      topicsUiState = topicsUiState.value,
      moodsUiState = echosViewModel.moodsUiState.collectAsStateWithLifecycle().value,
      onFilterAction = echosViewModel::onFilterAction,
      onReplayAction = echosViewModel::onReplayAction
   )
}