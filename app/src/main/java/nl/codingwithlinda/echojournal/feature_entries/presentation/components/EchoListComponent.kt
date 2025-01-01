package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic

@Composable
fun EchoListComponent(
    entries: List<UiEcho>,
    topics: List<UiTopic>,
    selectedMoods: String,
    selectedTopics: String
) {
    Column {

        FilterEchoComponent(
            modifier = Modifier.padding(start = 16.dp),
            topics = topics,
            selectedMoods = selectedMoods,
            selectedTopics = selectedTopics,

        )
        LazyColumn {
            items(entries) {uiEcho ->
                EchoListItem(
                    modifier = Modifier,
                    icon = {
                        Image(painter = painterResource(id = uiEcho.mood.icon), contentDescription = null)
                    },
                    content = {
                        EchoListItemContent(
                            modifier = Modifier.padding(start = 16.dp),
                            iconTint = Color(uiEcho.mood.color),
                            title = uiEcho.name,
                            timeStamp = uiEcho.timeStamp,
                            amplitudes = uiEcho.amplitudes,
                            duration = uiEcho.duration,
                            tags = uiEcho.topics,
                        )

                    }
                )
            }
        }
    }
}