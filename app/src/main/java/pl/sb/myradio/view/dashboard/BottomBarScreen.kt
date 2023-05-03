package pl.sb.myradio.view.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
  val route: String,
  val title: String,
  val icon: ImageVector
)
{
  object Player : BottomBarScreen(
    "player",
    "Player",
    Icons.Default.Home
  )
  object Stations : BottomBarScreen(
    "stations",
    "Stations",
    Icons.Default.List
  )
}
