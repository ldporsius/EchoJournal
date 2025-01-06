package nl.codingwithlinda.echojournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import nl.codingwithlinda.echojournal.core.application.EchoApplication.Companion.appModule
import nl.codingwithlinda.echojournal.core.data.AndroidEchoPlayer
import nl.codingwithlinda.echojournal.navigation.MainNav
import nl.codingwithlinda.echojournal.ui.theme.EchoJournalTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition(
            condition = {
                false
            }
        )
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            EchoJournalTheme {
                MainNav(
                    navController = navController,
                    appModule = appModule,
                    echoPlayer = AndroidEchoPlayer(this)
                )
            }

        }
    }
}
