package nl.codingwithlinda.echojournal.core.presentation.topics

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import nl.codingwithlinda.echojournal.core.presentation.topics.state.TopicAction
import nl.codingwithlinda.echojournal.core.presentation.topics.state.TopicAction.TopicChanged

@Composable
fun TopicInputComponent(
    modifier: Modifier = Modifier,
    topicText: String,
    onTopicAction: (TopicAction) -> Unit

) {
    val interactionSource = remember {
        object : MutableInteractionSource {
            override val interactions = MutableSharedFlow<Interaction>(
                extraBufferCapacity = 16,
                onBufferOverflow = BufferOverflow.DROP_OLDEST,
            )

            override suspend fun emit(interaction: Interaction) {
                when (interaction) {
                    is PressInteraction.Press -> {
                        onTopicAction(TopicAction.ShowHideTopics(true))
                    }
                }

                interactions.emit(interaction)
            }

            override fun tryEmit(interaction: Interaction): Boolean {
                return interactions.tryEmit(interaction)
            }
        }
    }
    OutlinedTextField(
        value = topicText,
        onValueChange = {
            onTopicAction(TopicChanged(it))
        },
        placeholder = {
            Text(
                "Topic",
                style = MaterialTheme.typography.labelSmall
            )
        },
        leadingIcon = {
           Text("#")
        },

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = androidx.compose.ui.text.input.ImeAction.Done
        ),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedTextColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedTextColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedLeadingIconColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = CircleShape,
        textStyle = MaterialTheme.typography.labelSmall,
        interactionSource = interactionSource,
        modifier = modifier
    )
}