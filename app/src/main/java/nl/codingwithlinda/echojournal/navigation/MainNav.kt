package nl.codingwithlinda.echojournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import nl.codingwithlinda.echojournal.core.data.EchoDto
import nl.codingwithlinda.echojournal.core.di.AppModule
import nl.codingwithlinda.echojournal.feature_create.presentation.CreateRoot
import nl.codingwithlinda.echojournal.feature_entries.presentation.EchosRoot
import nl.codingwithlinda.echojournal.feature_settings.presentation.SettingsRoot
import kotlin.reflect.typeOf

@Composable
fun MainNav(
  navController: NavHostController,
  appModule: AppModule,
) {

  NavHost(navController = navController, startDestination = EchosRoute) {
    composable<EchosRoute> {
      EchosRoot(
        appModule = appModule,
        navToCreateEcho = {
          navController.navigate(CreateEchoRoute(
            echoDto = it
          ))
        },
        navToSettings = {
          navController.navigate(SettingsRoute)
        }
      )
    }

    composable<CreateEchoRoute>(
      typeMap = mapOf(
        typeOf<EchoDto>() to CustomNavType.echoDtoNavType
      )
    ) {
      val arguments = it.toRoute<CreateEchoRoute>()
      CreateRoot(
        appModule = appModule,
        echoDto = arguments.echoDto,
        navigateBack = {
          navController.navigateUp()
        }
      )
    }

    composable<SettingsRoute>(){
      SettingsRoot(
        navBack = {
          navController.navigateUp()
        }
      )
    }
  }
}