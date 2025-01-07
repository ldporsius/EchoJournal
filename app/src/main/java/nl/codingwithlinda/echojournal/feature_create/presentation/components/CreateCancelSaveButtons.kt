package nl.codingwithlinda.echojournal.feature_create.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.presentation.components.GradientButton
import nl.codingwithlinda.echojournal.ui.theme.buttonDisabledGradient
import nl.codingwithlinda.echojournal.ui.theme.buttonGradient


@Composable
fun CreateCancelSaveButtons(
    modifier: Modifier = Modifier,
    canSave: Boolean,
    save: () -> Unit,
    cancel: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledTonalButton(
            onClick = {
                cancel()
            },
        ) {
            Text("Cancel")
        }

        GradientButton(
            text = {
                Text("Save")
            },
            gradient = if (canSave) buttonGradient else buttonDisabledGradient,
            onClick = {
               save()
            },
            enabled = canSave,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .weight(1f)
        )
    }
}