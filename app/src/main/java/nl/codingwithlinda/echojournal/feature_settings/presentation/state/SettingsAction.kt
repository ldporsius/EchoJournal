package nl.codingwithlinda.echojournal.feature_settings.presentation.state

import nl.codingwithlinda.core.model.Mood
import nl.codingwithlinda.core.model.Topic

sealed interface SettingsAction {
    data class SelectMoodAction(val mood: Mood): SettingsAction
    //data class SelectTopicAction(val topic: Topic): SettingsAction
}