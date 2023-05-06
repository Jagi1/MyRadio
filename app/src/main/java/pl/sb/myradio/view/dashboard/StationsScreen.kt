package pl.sb.myradio.view.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import pl.sb.myradio.model.Station
import pl.sb.myradio.R
import pl.sb.myradio.viewModel.dashboard.DashboardViewModel

@Composable
fun StationsScreen(
  viewModel: DashboardViewModel
)
{
  val newStationVisibilityState = viewModel.newStationVisibilityState.collectAsState()

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.Transparent)
      .padding(dimensionResource(id = R.dimen.spacing_large)),
    contentAlignment = Alignment.Center,
  ) {
    if (newStationVisibilityState.value) {
      AddNewStationScreen(viewModel)
    } else {
      StationsScreen(viewModel)
    }
  }
}

@Composable
fun BoxScope.AddNewStationScreen(
  viewModel: DashboardViewModel
) {
  WindowTitle("Add a new station")
  Row(
    modifier = Modifier
      .align(Alignment.BottomCenter)
  ) {
    Button(
      onClick = { viewModel.resolveUiEvent(DashboardViewModel.UiEvents.AddNewStationBackPressed) },
      modifier = Modifier
        .weight(1f)
    ) {
      Text(text = "Cancel")
    }
    Button(
      onClick = {  },
      modifier = Modifier
        .weight(1f)
        .padding(start = dimensionResource(id = R.dimen.spacing_base))
    ) {
      Text(text = "Confirm")
    }
  }
}

@Composable
fun BoxScope.StationsScreen(
  viewModel: DashboardViewModel
) {
  val stations by viewModel.stations.collectAsState()

  WindowTitle("Available stations")
  FloatingActionButton(
    onClick = {
      viewModel.resolveUiEvent(DashboardViewModel.UiEvents.AddNewStationClicked)
    },
    content = {
      Icon(
        painter = painterResource(id = R.drawable.add),
        contentDescription = "Add radio station"
      )
    },
    modifier = Modifier
      .align(alignment = Alignment.BottomEnd)
  )
  LazyColumn {
    items(
      items = stations,
      itemContent = {
        StationItem(it) {
          viewModel.resolveUiEvent(DashboardViewModel.UiEvents.StationClicked(it))
        }
      }
    )
  }
}

@Composable
fun BoxScope.WindowTitle(text: String) {
  Text(
    text = text,
    modifier = Modifier.align(Alignment.TopStart),
    style = TextStyle(
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold
    )
  )
}

@Composable
fun StationItem(station: Station, onClick: () -> Unit)
{
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color.White)
      .clickable(onClick = onClick)
  ) {
    Column {
      Text(text = station.name)
      Text(text = station.url)
    }
  }
}

@Preview
@Composable
private fun StationItemPreview()
{
  StationItem(station = Station("Station name", "Station url")) { }
}