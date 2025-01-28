package nl.codingwithlinda.echojournal.feature_settings.presentation.state

import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.core.domain.model.Topic

sealed interface SettingsAction {
    data class SelectMoodAction(val mood: Mood): SettingsAction
    //data class SelectTopicAction(val topic: Topic): SettingsAction
}