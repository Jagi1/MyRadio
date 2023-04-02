package pl.sb.myradio.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BottomNavGraph(
  navController: NavHostController,
  stations: StateFlow<List<String>>
)
{
  NavHost(
    navController = navController,
    startDestination = BottomBarScreen.Player.route
  ) {
    composable(
      route = BottomBarScreen.Player.route
    ) {
      PlayerScreen()
    }
    composable(
      route = BottomBarScreen.Stations.route
    ) {
      StationsScreen(stations)
    }
  }
}