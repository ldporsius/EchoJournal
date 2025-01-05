package nl.codingwithlinda.echojournal.feature_create.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState

@Composable
fun CreateEchoScreen(
    modifier: Modifier = Modifier,
    uiState: CreateEchoUiState = CreateEchoUiState(),
    onAction: (CreateEchoAction) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        TextField(
            value = uiState.title,
            onValueChange = {
                onAction(CreateEchoAction.TitleChanged(it))
            },
            label = {
                Text("Add Title")
            },
            leadingIcon = {
                Icon(Icons.Default.AddCircle, contentDescription = null)
            }
        )


    }
}