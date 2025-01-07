package nl.codingwithlinda.echojournal.feature_create.presentation.state

import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood

sealed interface CreateEchoAction {
    data object PlayEcho: CreateEchoAction
    data class TitleChanged(val title: String): CreateEchoAction
    data class DescriptionChanged(val description: String): CreateEchoAction
    data class TopicChanged(val topic: String): CreateEchoAction
    data class ShowHideTopics(val visible: Boolean): CreateEchoAction
    data class ShowHideMoods(val visible: Boolean): CreateEchoAction
    data class SelectTopic(val topic: String): CreateEchoAction
    data class CreateTopic(val topic: String): CreateEchoAction
    data class RemoveTopic(val topic: String): CreateEchoAction
    data class SelectMood(val mood: UiMood): CreateEchoAction
    data class ConfirmMood(val mood: UiMood): CreateEchoAction
    data object Save: CreateEchoAction



}