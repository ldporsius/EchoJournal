package nl.codingwithlinda.echojournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nl.codingwithlinda.echojournal.feature_entries.EntriesRoot

@Composable
fun MainNav(navController: NavHostController) {

  NavHost(navController = navController, startDestination = EntriesRoute) {
    composable<EntriesRoute> {
      EntriesRoot()
    }
  }
}