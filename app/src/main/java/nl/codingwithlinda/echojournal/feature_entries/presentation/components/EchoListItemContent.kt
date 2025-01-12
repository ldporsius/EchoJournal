package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho

@Composable
fun EchoListItemContent(
    modifier: Modifier = Modifier,
    uiEcho: UiEcho,
    onFilterTopic: (Topic) -> Unit,
   replayComponent: @Composable () -> Unit,
) {

    val title = uiEcho.name
    val timeStamp = uiEcho.timeStamp

    val tags = uiEcho.topics


    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
            ,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            Text(title)
            Text(timeStamp)
        }


        replayComponent()

        EchoDescriptionComponent(
            description = uiEcho.description,
        )

       TopicsComponent(
           tags = tags,
           onClick = {
               onFilterTopic(it)
           },
           moodColor = Color(uiEcho.mood.color).copy(.05f),
           modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
       )
    }
}
