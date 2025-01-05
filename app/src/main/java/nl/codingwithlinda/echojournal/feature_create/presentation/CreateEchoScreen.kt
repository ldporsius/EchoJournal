package nl.codingwithlinda.echojournal.feature_create.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.presentation.components.EchoPlaybackComponent
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEchoScreen(
    modifier: Modifier = Modifier,
    uiState: CreateEchoUiState = CreateEchoUiState(),
    onAction: (CreateEchoAction) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { }
            ) {
                Icon(Icons.Default.AddCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.size(48.dp)
                )
            }
            OutlinedTextField(
                value = uiState.title,
                onValueChange = {
                    onAction(CreateEchoAction.TitleChanged(it))
                },
                placeholder = {
                    Text("Add Title ...",
                        style = MaterialTheme.typography.titleLarge

                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    unfocusedTextColor = MaterialTheme.colorScheme.secondaryContainer,
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        EchoPlaybackComponent(
            onAction = {},
            moodColor = androidx.compose.ui.graphics.Color.Gray,
            duration = "1:23",
            amplitudes = listOf(),
            echoId = ""
        )

        //topic
        val isTopicsExpanded = uiState.isTopicsExpanded
        ExposedDropdownMenuBox(
            expanded = true,
            onExpandedChange = {
                println("onExpandedChange")
            }
        ) {

            OutlinedTextField(
                modifier = Modifier.menuAnchor(),
                value = uiState.topic,
                onValueChange = {
                    onAction(CreateEchoAction.TopicChanged(it))
                },
                placeholder = {
                    Text(
                        "Topic",
                        style = MaterialTheme.typography.titleSmall

                    )
                },
                leadingIcon = {
                    Text("#")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    unfocusedTextColor = MaterialTheme.colorScheme.secondaryContainer,
                )
            )
            ExposedDropdownMenu(
                expanded = isTopicsExpanded,
                onDismissRequest = {

                }
            ) {
                uiState.topics.forEach{
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {

                        }
                    )
                }
            }
        }

        //description
        OutlinedTextField(
            value = uiState.title,
            onValueChange = {
                onAction(CreateEchoAction.TitleChanged(it))
            },
            placeholder = {
                Text("Add Description",
                    style = MaterialTheme.typography.titleSmall

                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                unfocusedTextColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )
    }
}