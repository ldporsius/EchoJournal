package nl.codingwithlinda.echojournal.feature_create.presentation.state

import nl.codingwithlinda.core.model.Topic
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood

sealed interface CreateEchoAction {
    data object PlayEcho: CreateEchoAction
    data object PauseEcho: CreateEchoAction
    data class TitleChanged(val title: String): CreateEchoAction
    data class DescriptionChanged(val description: String): CreateEchoAction

    data class ShowHideMoods(val visible: Boolean): CreateEchoAction

    data class SelectMood(val mood: UiMood): CreateEchoAction
    data class ConfirmMood(val mood: UiMood): CreateEchoAction
    data object Save: CreateEchoAction



}