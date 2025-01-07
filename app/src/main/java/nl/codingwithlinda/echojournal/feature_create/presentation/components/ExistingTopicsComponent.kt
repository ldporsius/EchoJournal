package nl.codingwithlinda.echojournal.feature_create.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExistingTopicsComponent(
    modifier: Modifier = Modifier,
    selectedTopics: List<String>,
    onAction: (CreateEchoAction) -> Unit
) {

    val topicModifier = Modifier
        .background(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = CircleShape
        ).heightIn(32.dp, 32.dp)
        .padding(horizontal = 8.dp)

        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                ,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            selectedTopics.forEach {
                Row(
                    modifier = topicModifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "# $it",
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = TextUnit(12f, TextUnitType.Sp),
                        modifier = Modifier
                    )
                    IconButton(
                        onClick = {
                            onAction(CreateEchoAction.RemoveTopic(it))
                        },
                        modifier = Modifier.size(18.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            }


            TextButton(
                onClick = {
                    onAction(CreateEchoAction.ShowHideTopics(true))
                },
                modifier =
                        Modifier
                            .heightIn(32.dp, 32.dp)
                            .background(color = Color.Transparent, shape = CircleShape)


            ) {
                val txt = "Add topic"
                Text("# $txt"
                    , style = MaterialTheme.typography.labelSmall)
            }

    }
}