package pl.sb.myradio.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.StateFlow
import pl.sb.myradio.model.Station
import pl.sb.myradio.viewModel.DashboardViewModel

@Composable
fun BottomNavGraph(
  navController: NavHostController,
  viewModel: DashboardViewModel,
  innerPadding: PaddingValues
)
{
  NavHost(
    navController = navController,
    startDestination = BottomBarScreen.Player.route,
    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
  ) {
    composable(
      route = BottomBarScreen.Player.route
    ) {
      PlayerScreen(viewModel)
    }
    composable(
      route = BottomBarScreen.Stations.route
    ) {
      StationsScreen(viewModel)
    }
  }
}