package nl.codingwithlinda.echojournal.feature_entries.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nl.codingwithlinda.echojournal.core.data.EchoDto
import nl.codingwithlinda.echojournal.core.di.AppModule
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterDuration
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EchosScreen
import nl.codingwithlinda.echojournal.core.data.EchoFactory
import nl.codingwithlinda.echojournal.feature_record.presentation.RecordAudioViewModel

@Composable
fun EchosRoot(
   appModule: AppModule,
   echoPlayer: EchoPlayer,
   navToCreateEcho: (EchoDto) -> Unit
) {

   val echoesFactory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
         EchosViewModel(
            echoPlayer = echoPlayer
         )
      }
   }
   val recordFactory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
         RecordAudioViewModel(
            recorder = appModule.audioRecorder,
            dateTimeFormatter = DateTimeFormatterDuration(),
            echoFactory = EchoFactory(),
            navToCreateEcho = {
               navToCreateEcho(it)
            }
         )
      }
   }

   val echosViewModel = viewModel<EchosViewModel>(
      factory = echoesFactory
   )
   val recordAudioViewModel = viewModel<RecordAudioViewModel>(
      factory = recordFactory
   )
   val topicsUiState = echosViewModel.topicsUiState
      .collectAsStateWithLifecycle()

   EchosScreen(
      echoesUiState = echosViewModel.echoesUiState.collectAsStateWithLifecycle().value,
      topicsUiState = topicsUiState.value,
      moodsUiState = echosViewModel.moodsUiState.collectAsStateWithLifecycle().value,
      onFilterAction = echosViewModel::onFilterAction,
      onReplayAction = echosViewModel::onReplayAction,
      recordAudioUiState = recordAudioViewModel.uiState.collectAsStateWithLifecycle().value,
      onRecordAudioAction = recordAudioViewModel::onAction
   )
}