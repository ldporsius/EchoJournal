package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import android.view.MotionEvent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.EchoesUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.FilterEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.MoodsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.applyIf
import nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode.RecordAudioComponent
import nl.codingwithlinda.echojournal.feature_record.presentation.components.quick_mode.RecordingModeQuickComponent
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.AddRecordingComponent
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioAction
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioUiState
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordingMode


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun EchosScreen(
    echoesUiState: EchoesUiState,
    moodsUiState: MoodsUiState,
    topicsUiState: TopicsUiState,
    replayUiState: (uiEcho: UiEcho) -> ReplayUiState,
    onFilterAction: (FilterEchoAction) -> Unit,
    onReplayAction: (ReplayEchoAction) -> Unit,
    recordAudioUiState: RecordAudioUiState,
    onRecordAudioAction: (RecordAudioAction) -> Unit,
) {

    Scaffold(
        modifier = Modifier.safeContentPadding(),
        topBar = { EchosTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) },
        floatingActionButton = {
        }
    ) { padding ->
        Box(modifier =  Modifier
            .fillMaxSize()
            .padding(padding))
        {
            Column(
                modifier = Modifier
            ) {

                if (echoesUiState.echoesTotal == 0) {
                    EmptyListComponent(
                        Modifier.fillMaxSize()
                    )
                } else {
                    EchoListComponent(
                        entries = echoesUiState.selectedEchoes,
                        moodsUiState = moodsUiState,
                        topicsUiState = topicsUiState,
                        replayUiState = {
                            replayUiState(it)
                        },
                        onFilterAction = onFilterAction,
                        onReplayAction = onReplayAction,
                    )
                }
            }

            Box(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .applyIf(
                    condition = false,
                    modifier = Modifier
                        .pointerInteropFilter {motionEvent ->
                        if (motionEvent.action == MotionEvent.ACTION_UP) {
                            //onRecordAudioAction(RecordAudioAction.SaveRecording)
                        }
                        true
                    }
                )

            ) {

                RecordingModeQuickComponent(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .fillMaxWidth()
                        .padding(16.dp)
                    ,
                    onAction = onRecordAudioAction,
                    onTap = {
                        onRecordAudioAction(RecordAudioAction.ChangeRecordingMode(RecordingMode.DELUXE))
                        onRecordAudioAction(RecordAudioAction.StartRecording)
                    }
                )

            }

            if (recordAudioUiState.shouldShowRecordDeluxeComponent) {

                ModalBottomSheet(
                    onDismissRequest = {
                        onRecordAudioAction(RecordAudioAction.ToggleVisibility)
                        onRecordAudioAction(RecordAudioAction.ChangeRecordingMode(RecordingMode.QUICK))
                                       },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RecordAudioComponent(
                        modifier = Modifier,
                        uiState = recordAudioUiState,
                        onAction = onRecordAudioAction
                    )
                }
            }
        }
    }

}