package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.core.model.Topic
import nl.codingwithlinda.echojournal.ui.theme.primary20
import nl.codingwithlinda.echojournal.ui.theme.surfaceTint

@Composable
fun SelectTopicComponent(
    modifier: Modifier = Modifier,
    topics: List<Topic>,
    isSelected: (Topic) -> Boolean,
    onTopicSelected: (Topic) -> Unit,
    onDismiss: () -> Unit
) {
    
    val paddingMargin = 4.dp
    DropdownMenu(
        modifier = Modifier
            .fillMaxWidth(.98f)
            .background(
                color = MaterialTheme.colorScheme.surface,
            )
            .padding(horizontal = 8.dp),
        expanded = true,
        onDismissRequest = {
            onDismiss()
        },
        offset = DpOffset(
            x = paddingMargin,
            y = 0.dp
        )
    ) {

        topics.forEach {

            val bgColor = if (isSelected(it)) {
                surfaceTint.copy(.05f)
            } else {
                Color.Transparent
            }
            DropdownMenuItem(
                modifier = Modifier.background(
                    color = bgColor,
                    shape = RoundedCornerShape(100)
                ),
                text = {
                        Text(it.name)
                },
                onClick = {
                    onTopicSelected(it)
                },
                leadingIcon = {
                    Text("#")
                },
                trailingIcon = {
                    if (isSelected(it)) {
                        Icon(imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = primary20
                        )
                    }
                },
                colors = MenuDefaults.itemColors(

                )
            )
            Spacer(modifier = Modifier.height(2.dp))
        }


    }
}