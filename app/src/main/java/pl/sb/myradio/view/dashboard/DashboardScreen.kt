package pl.sb.myradio.view.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pl.sb.myradio.R
import pl.sb.myradio.viewModel.dashboard.DashboardViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
  viewModel: DashboardViewModel
)
{
  Box {
    BackgroundImage()
    DashboardScaffold(viewModel)
  }
}

@Composable
fun BoxScope.BackgroundImage() {
  Image(
    painter = painterResource(id = R.drawable.background),
    contentDescription = "",
    contentScale = ContentScale.FillBounds,
//    modifier = Modifier.fillMaxSize().blur(radiusX = 12.dp, radiusY = 24.dp)
  )
}

@Composable
fun BoxScope.DashboardScaffold(viewModel: DashboardViewModel) {
  val navController = rememberNavController()
  Scaffold(
    bottomBar = { BottomBar(navController = navController) },
    backgroundColor = Color.Transparent,
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

  Box {
    Surface(
      color = Color.Transparent,
      contentColor = contentColorFor(Color.Transparent),
      elevation = 0.dp
    ) {
      Row(
        Modifier
          .wrapContentWidth()
          .height(56.dp)
          .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
          screens
            .forEach { screen ->
              ScreenOption(screen = screen, currentDestination = currentDestination, navController = navController)
            }
        }
      )
    }
  }
}

@Composable
fun RowScope.ScreenOption(
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
    unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
    modifier = Modifier.background(Color.Transparent)
  )
}