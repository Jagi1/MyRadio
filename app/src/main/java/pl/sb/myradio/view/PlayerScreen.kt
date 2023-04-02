package pl.sb.myradio.view

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.sb.myradio.viewModel.DashboardViewModel

@Composable
fun PlayerScreen()
{
  val viewModel = viewModel<DashboardViewModel>(LocalContext.current as AppCompatActivity)

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.Magenta),
    contentAlignment = Alignment.Center
  ) {
    Button(
      onClick = {
        viewModel.getStations()
      }
    ) {

    }
  }
}