package pl.sb.myradio.view.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.sb.myradio.viewModel.dashboard.DashboardViewModel

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
    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()).background(Color.Transparent)
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