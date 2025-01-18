package nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.permission

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import nl.codingwithlinda.echojournal.MainActivity
import org.junit.Rule
import org.junit.Test

class AskPermissionComponentKtTest{

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testAskPermissionComponent() {
        composeTestRule.waitUntilExactlyOneExists(
            hasTestTag( "RECORDER_MAIN_BUTTON"),
            timeoutMillis = 5000
        )
        composeTestRule.onNodeWithTag("RECORDER_MAIN_BUTTON").performClick()

    }
}