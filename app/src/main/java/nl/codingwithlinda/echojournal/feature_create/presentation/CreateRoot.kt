package nl.codingwithlinda.echojournal.feature_create.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.codingwithlinda.echojournal.core.data.EchoDto
import nl.codingwithlinda.echojournal.core.di.AppModule

@Composable
fun CreateRoot(
    appModule: AppModule,
    echoDto: EchoDto
) {
    Scaffold {
        CreateEchoScreen(
            Modifier.fillMaxSize()
                .padding(it)
        )
    }
}