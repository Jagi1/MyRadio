package pl.sb.myradio.view

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import pl.sb.myradio.model.Station
import pl.sb.myradio.viewModel.DashboardViewModel

@Composable
fun StationsScreen(
  viewModel: DashboardViewModel
)
{
  val stations by viewModel.stations.collectAsState()

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.White),
    contentAlignment = Alignment.Center
  ) {
    LazyColumn() {
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
private fun StationItemPreview() {
  StationItem(station = Station("Station name", "Station url")) { }
}