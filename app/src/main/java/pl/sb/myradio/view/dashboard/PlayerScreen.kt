package pl.sb.myradio.view.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import pl.sb.myradio.R
import pl.sb.myradio.viewModel.dashboard.DashboardViewModel

@Composable
fun PlayerScreen(
  viewModel: DashboardViewModel
)
{
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.White),
    contentAlignment = Alignment.Center
  ) {
    Station(viewModel)
    StationControls(viewModel)
  }
}

@Composable
private fun BoxScope.Station(
  viewModel: DashboardViewModel
)
{
  val chosenStation by viewModel.chosenStation.collectAsState()

  Column {
    chosenStation?.let { station ->
      Text(text = station.name)
      Text(text = station.url)
    }
  }
}

@Composable
private fun BoxScope.StationControls(
  viewModel: DashboardViewModel
)
{
  Row(modifier = Modifier.align(alignment = Alignment.BottomCenter)) {
    Image(
      painter = painterResource(id = R.drawable.play_arrow),
      contentDescription = "Play button",
      modifier = Modifier
        .clickable { viewModel.resolveUiEvent(DashboardViewModel.UiEvents.PlayButtonClicked) }
    )
    Image(
      painter = painterResource(id = R.drawable.pause),
      contentDescription = "Pause button",
      modifier = Modifier
        .clickable { viewModel.resolveUiEvent(DashboardViewModel.UiEvents.PauseButtonClicked) }
    )
    Image(
      painter = painterResource(id = R.drawable.stop),
      contentDescription = "Stop button",
      modifier = Modifier
        .clickable { viewModel.resolveUiEvent(DashboardViewModel.UiEvents.StopButtonClicked) }
    )
  }
}