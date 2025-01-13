package nl.codingwithlinda.echojournal.feature_entries.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.applyIf(
    condition: Boolean,
    modifier: Modifier = Modifier
): Modifier {
    return if (condition) {
        then(modifier)
    } else {
        this
    }
}