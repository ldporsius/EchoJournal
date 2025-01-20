package nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.permission

import android.content.Context
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.echojournal.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

class AskPermissionComponentKtTest{

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testAskPermissionComponent() = runBlocking<Unit>{
        val screenshotDir = File(context.filesDir, "screenshots")
        composeTestRule.awaitIdle()
        composeTestRule.waitUntilExactlyOneExists(
            hasTestTag( "RECORDER_MAIN_BUTTON"),
            timeoutMillis = 5000
        )
        composeTestRule.onNodeWithTag("RECORDER_MAIN_BUTTON").performClick()


        composeTestRule.waitForIdle()
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.takeScreenshot(screenshotDir)

        Thread.sleep(5000)
    }
}