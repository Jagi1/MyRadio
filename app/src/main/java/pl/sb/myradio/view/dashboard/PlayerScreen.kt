package pl.sb.myradio.view.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pl.sb.myradio.R
import pl.sb.myradio.view.base.BlurryCard
import pl.sb.myradio.view.base.WindowTitle
import pl.sb.myradio.viewModel.dashboard.DashboardViewModel

@Composable
fun PlayerScreen(
  viewModel: DashboardViewModel
)
{
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(start = 24.dp, end = 24.dp, top = 24.dp)) {
    Column(
      modifier = Modifier
        .weight(1f)
    ) {
      WindowTitle("Current station")
      StationImage(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(1f)
          .padding(top = 16.dp)
      )
      StationInfo(
        viewModel = viewModel,
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 16.dp)
      )
    }
    StationControls(
      viewModel = viewModel,
      modifier = Modifier
        .padding(top = 16.dp)
        .fillMaxWidth()
    )
  }
}

@Composable
private fun StationImage(modifier: Modifier)
{
  BlurryCard(
    modifier = modifier
  ) {
    Image(
      painter = painterResource(id = R.drawable.default_station),
      contentDescription = "Station logo",
      contentScale = ContentScale.FillBounds
    )
  }
}

@Composable
private fun StationInfo(
  viewModel: DashboardViewModel,
  modifier: Modifier
)
{
  val chosenStation by viewModel.chosenStation.collectAsState()

  chosenStation?.let { station ->
    BlurryCard(modifier = modifier) {
      Column(modifier = Modifier.padding(8.dp)) {
        Text(text = station.name, style = MaterialTheme.typography.subtitle1)
        Text(text = station.url, style = MaterialTheme.typography.subtitle2)
      }
    }
  }
}

@Composable
private fun StationControls(
  viewModel: DashboardViewModel,
  modifier: Modifier
)
{
  BlurryCard(modifier = modifier) {
    Row {
      Image(
        painter = painterResource(id = R.drawable.play_arrow),
        contentDescription = "Play button",
        modifier = Modifier
          .clickable { viewModel.resolveUiEvent(DashboardViewModel.UiEvents.PlayButtonClicked) }
          .weight(1f)
      )
      Image(
        painter = painterResource(id = R.drawable.pause),
        contentDescription = "Pause button",
        modifier = Modifier
          .clickable { viewModel.resolveUiEvent(DashboardViewModel.UiEvents.PauseButtonClicked) }
          .weight(1f)
      )
      Image(
        painter = painterResource(id = R.drawable.stop),
        contentDescription = "Stop button",
        modifier = Modifier
          .clickable { viewModel.resolveUiEvent(DashboardViewModel.UiEvents.StopButtonClicked) }
          .weight(1f)
      )
    }
  }
}