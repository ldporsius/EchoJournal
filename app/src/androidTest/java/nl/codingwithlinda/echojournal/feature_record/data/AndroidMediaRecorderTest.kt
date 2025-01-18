package nl.codingwithlinda.echojournal.feature_record.data

import android.app.Application
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import nl.codingwithlinda.echojournal.core.di.AndroidAppModule
import org.junit.Rule
import org.junit.Test
import android.Manifest
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import nl.codingwithlinda.echojournal.MainActivity

class AndroidMediaRecorderTest{

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.RECORD_AUDIO)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    val context = ApplicationProvider.getApplicationContext<Application>()
    val appModule = AndroidAppModule(context)

    val dispatcherProvider =  appModule.dispatcherProvider

    val recorder = AndroidMediaRecorder(context, dispatcherProvider)


    @Test
    fun testAndroidMediaRecorder(){
        recorder.start()

        recorder.stop()

    }
}