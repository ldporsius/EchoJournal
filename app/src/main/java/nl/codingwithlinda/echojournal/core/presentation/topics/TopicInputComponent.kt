package nl.codingwithlinda.echojournal.core.presentation.topics

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import nl.codingwithlinda.echojournal.core.presentation.topics.state.TopicAction
import nl.codingwithlinda.echojournal.core.presentation.topics.state.TopicAction.TopicChanged

@Composable
fun TopicInputComponent(
    modifier: Modifier = Modifier,
    topicText: String,
    onTopicAction: (TopicAction) -> Unit

) {

    OutlinedTextField(
        value = topicText,
        onValueChange = {
            onTopicAction(TopicChanged(it))
        },
        placeholder = {
            Text(
                "# Topic",
                style = MaterialTheme.typography.labelSmall
            )
        },
        leadingIcon = {
            //Text("#")
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
        ),
        shape = CircleShape,
        textStyle = MaterialTheme.typography.labelSmall,
        modifier = modifier
    )
}