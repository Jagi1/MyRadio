package pl.sb.myradio.view.dashboard

import android.content.Context.AUDIO_SERVICE
import android.media.AudioManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
          .padding(top = 8.dp)
      )
      StationInfo(
        viewModel = viewModel,
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp)
      )
    }
    VolumeSlider(
      viewModel = viewModel,
      modifier = Modifier
        .padding(top = 8.dp)
        .fillMaxWidth()
    )
    StationControls(
      viewModel = viewModel,
      modifier = Modifier
        .padding(top = 8.dp)
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
private fun VolumeSlider(
  viewModel: DashboardViewModel,
  modifier: Modifier
)
{
  val soundVolume by viewModel.soundVolume.collectAsState()
  val isSoundMuted by viewModel.isSoundMuted.collectAsState()
  BlurryCard(modifier = modifier) {
    Row {
      Image(
        painter = when (isSoundMuted?.muted ?: false)
        {
          true -> painterResource(id = R.drawable.volume_off)
          else -> painterResource(id = R.drawable.volume_up)
        },
        contentDescription = "",
        modifier = Modifier
          .clickable {
            viewModel.resolveUiEvent(DashboardViewModel.UiEvents.MuteSoundButtonClicked)
          }
          .align(CenterVertically)
          .padding(end = 2.dp, start = 8.dp)
      )
      Slider(
        modifier = Modifier.weight(1f),
        value = soundVolume?.currentPercent ?: 0f,
        enabled = isSoundMuted?.muted?.not() ?: false,
        valueRange = 0f..100f,
        steps = 10,
        onValueChange = {
          viewModel.resolveUiEvent(DashboardViewModel.UiEvents.VolumeSliderSwiped(it))
        }
      )
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