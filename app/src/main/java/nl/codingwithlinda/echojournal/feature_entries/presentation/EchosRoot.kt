package nl.codingwithlinda.echojournal.feature_entries.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nl.codingwithlinda.echojournal.core.data.EchoDto
import nl.codingwithlinda.echojournal.core.di.AppModule
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterDuration
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EchosScreen
import nl.codingwithlinda.echojournal.feature_record.presentation.RecordAudioViewModel
import nl.codingwithlinda.echojournal.feature_record.presentation.RecordingComponent
import nl.codingwithlinda.echojournal.feature_record.presentation.components.quick_mode.RecordQuickViewModel

@Composable
fun EchosRoot(
   appModule: AppModule,
   navToCreateEcho: (EchoDto) -> Unit,
   navToSettings: () -> Unit
) {

   val echoesFactory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
         EchosViewModel(
            echoAccess = appModule.echoAccess,
            topicsAccess = appModule.topicsAccess,
            echoPlayer = appModule.echoPlayer,
            dateTimeFormatter = DateTimeFormatterDuration
         )
      }
   }
   val recordFactory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
         RecordAudioViewModel(
            dispatcherProvider = appModule.dispatcherProvider,
            recorder = appModule.audioRecorder,
            dateTimeFormatter = DateTimeFormatterDuration,
            echoFactory = appModule.echoFactory,
            navToCreateEcho = {
               navToCreateEcho(it)
            }
         )
      }
   }

   val quickRecordFactory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
         RecordQuickViewModel(
            audioRecorder = appModule.audioRecorder
         )
      }
   }
   val echosViewModel = viewModel<EchosViewModel>(
      factory = echoesFactory
   )
   val recordAudioViewModel = viewModel<RecordAudioViewModel>(
      factory = recordFactory
   )
   val quickActionViewModel = viewModel<RecordQuickViewModel>(
      factory = quickRecordFactory
   )

   val topicsUiState = echosViewModel.topicsUiState
      .collectAsStateWithLifecycle()

   val replayUiState = echosViewModel.replayUiState.collectAsStateWithLifecycle().value
   EchosScreen(
      echoesUiState = echosViewModel.echoesUiState.collectAsStateWithLifecycle().value,
      topicsUiState = topicsUiState.value,
      moodsUiState = echosViewModel.moodsUiState.collectAsStateWithLifecycle().value,
      replayUiState = {
         replayUiState.getValue(it.id)
      }
      ,
      onFilterAction = echosViewModel::onFilterAction,
      onReplayAction = echosViewModel::onReplayAction,
      recordingComponent = {
         RecordingComponent(
             modifier = Modifier,
             recordAudioUiState = recordAudioViewModel.uiState.collectAsStateWithLifecycle().value,
             onQuickAction = quickActionViewModel::handleAction,
             onRecordAudioAction = recordAudioViewModel::onAction,
         )
      },
      navToSettings = navToSettings
   )
}