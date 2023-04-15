package pl.sb.myradio.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pl.sb.myradio.viewModel.DashboardViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
  viewModel: DashboardViewModel
)
{
  val navController = rememberNavController()
  Scaffold(
    topBar = {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(MaterialTheme.colors.primary),
        
      ) {
        Text(text = stringResource(id = pl.sb.myradio.R.string.app_name))
      }
    },
    bottomBar = { BottomBar(navController = navController) },

  ) { innerPadding ->
    BottomNavGraph(navController = navController, viewModel, innerPadding)
  }
}

@Composable
fun BottomBar(navController: NavHostController)
{
  val screens = listOf(
    BottomBarScreen.Player,
    BottomBarScreen.Stations
  )
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentDestination = navBackStackEntry?.destination

  BottomNavigation {
    screens
      .forEach { screen ->
        AddScreen(screen = screen, currentDestination = currentDestination, navController = navController)
      }
  }
}

@Composable
fun RowScope.AddScreen(
  screen: BottomBarScreen,
  currentDestination: NavDestination?,
  navController: NavHostController
)
{
  BottomNavigationItem(
    label = {
      Text(text = screen.title)
    },
    icon = {
      Icon(imageVector = screen.icon, contentDescription = "Navigation icon")
    },
    selected = currentDestination?.hierarchy?.any { destination -> destination.route == screen.route } == true,
    onClick = {
      navController.navigate(screen.route) {
        popUpTo(navController.graph.findStartDestination().id)
        launchSingleTop = true
      }
    },
    unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
  )
}