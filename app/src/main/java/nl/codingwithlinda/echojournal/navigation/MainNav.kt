package nl.codingwithlinda.echojournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nl.codingwithlinda.echojournal.feature_entries.presentation.EchosRoot

@Composable
fun MainNav(navController: NavHostController) {

  NavHost(navController = navController, startDestination = EchosRoute) {
    composable<EchosRoute> {
      EchosRoot()
    }
  }
}