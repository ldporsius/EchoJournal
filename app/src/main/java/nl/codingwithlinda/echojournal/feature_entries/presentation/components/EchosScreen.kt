package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.EchoesUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.FilterEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.MoodsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho
import nl.codingwithlinda.echojournal.feature_record.presentation.components.RecordAudioComponent
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioAction
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioUiState
import nl.codingwithlinda.echojournal.ui.theme.buttonGradient


@OptIn(ExperimentalMaterial3Api::class)
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

                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clickable {
                            onRecordAudioAction(RecordAudioAction.ToggleVisibility)
                        }
                        .background(
                        brush = buttonGradient,
                            shape = CircleShape
                    ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            if (echoesUiState.echoesTotal == 0) {
               EmptyListComponent(
                   Modifier.fillMaxSize()
               )
            }
            else{
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

        if (recordAudioUiState.shouldShowRecordAudioComponent){
            ModalBottomSheet(
                onDismissRequest = { onRecordAudioAction(RecordAudioAction.ToggleVisibility)},
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