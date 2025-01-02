package nl.codingwithlinda.echojournal.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {

    data class DynamicString(val value: String): UiText

    data class StringResource(val resId: Int, val args: List<Any> = emptyList()): UiText

    @Composable
    fun asString(): String{
        return when(this){
            is DynamicString -> value
            is StringResource -> stringResource(resId, args)
        }
    }
}