package nl.codingwithlinda.echojournal.feature_create.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.codingwithlinda.echojournal.core.data.EchoDto
import nl.codingwithlinda.echojournal.core.di.AppModule
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRoot(
    appModule: AppModule,
    echoDto: EchoDto,
    navigateBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.safeContentPadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("New entry",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigateBack()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Default.KeyboardArrowLeft, contentDescription = "Back")
                    }
                }
            )
        },

    ) {
        CreateEchoScreen(
            Modifier.fillMaxSize()
                .padding(it),
            uiState = CreateEchoUiState(),
            onAction = {}
        )
    }
}