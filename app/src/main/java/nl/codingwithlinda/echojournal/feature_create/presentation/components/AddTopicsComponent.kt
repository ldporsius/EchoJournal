package nl.codingwithlinda.echojournal.feature_create.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction

@Composable
fun AddTopicComponent(
    modifier: Modifier = Modifier,
    topic: String,
    topics: List<String>,
    isTopicsExpanded: Boolean,
    shouldShowCreate: Boolean,
    onAction: (CreateEchoAction) -> Unit,
) {
    var dropdownOffset by remember { mutableStateOf(DpOffset.Zero) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onAction(CreateEchoAction.ShowHideTopics(true))
            }

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = topic,
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
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onAction(CreateEchoAction.ShowHideTopics(false))
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close topics")
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = androidx.compose.ui.text.input.ImeAction.Done
                ),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    unfocusedTextColor = MaterialTheme.colorScheme.secondaryContainer,
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(16.dp),
                        spotColor = Color.Black,

                        )
                    .background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))

            ) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()

                    ) {
                    topics.forEach { topic ->
                        DropdownMenuItem(
                            text = { Text(topic) },
                            leadingIcon = {
                                Text("#")
                            },
                            onClick = {
                                onAction(CreateEchoAction.SelectTopic(topic))
                            }
                        )
                    }

                    AnimatedVisibility(shouldShowCreate) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "+ Create '${topic}'",
                                    color = MaterialTheme.colorScheme.primary
                                )
                            },
                            onClick = {
                                onAction(CreateEchoAction.ShowHideTopics(false))
                                onAction(CreateEchoAction.CreateTopic(topic))
                            }
                        )
                    }

                }
            }
            }
        }
}