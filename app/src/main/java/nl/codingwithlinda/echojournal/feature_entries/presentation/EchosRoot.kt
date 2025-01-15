package nl.codingwithlinda.echojournal.feature_entries.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import nl.codingwithlinda.echojournal.core.data.EchoDto
import nl.codingwithlinda.echojournal.core.di.AppModule
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterDuration
import nl.codingwithlinda.echojournal.core.presentation.util.UiText
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EchosScreen
import nl.codingwithlinda.echojournal.feature_record.presentation.RecordAudioViewModel
import nl.codingwithlinda.echojournal.feature_record.presentation.RecordingComponent
import nl.codingwithlinda.echojournal.feature_record.presentation.components.quick_mode.RecordQuickViewModel
import nl.codingwithlinda.echojournal.feature_record.util.toUiText

@Composable
fun EchosRoot(
   appModule: AppModule,
   navToCreateEcho: (EchoDto) -> Unit,
   navToSettings: () -> Unit
) {
   var error:UiText? by remember {
      mutableStateOf(null)
   }
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
            },
            onRecordingFailed = {
               error = it.toUiText()
            }
         )
      }
   }

   val quickRecordFactory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
         RecordQuickViewModel(
            audioRecorder = appModule.audioRecorder,
            echoFactory = appModule.echoFactory,
            onRecordingFinished = {
               navToCreateEcho(it)
            },
            onRecordingFailed = {
               error = it.toUiText()
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
   val quickActionViewModel = viewModel<RecordQuickViewModel>(
      factory = quickRecordFactory
   )

   val topicsUiState = echosViewModel.topicsUiState
      .collectAsStateWithLifecycle()

   val replayUiState = echosViewModel.replayUiState.collectAsStateWithLifecycle().value

   val context = LocalContext.current

   error?.let {
      Toast.makeText(context, it.asString(), Toast.LENGTH_LONG).show()
   }

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