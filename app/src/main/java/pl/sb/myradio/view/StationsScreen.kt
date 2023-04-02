package pl.sb.myradio.view

import android.os.Debug
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.foundation.lazy.items

@Composable
fun StationsScreen(
  stations: StateFlow<List<String>>
)
{
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.White),
    contentAlignment = Alignment.Center
  ) {
    LazyColumn() {
      items(
        items = stations.value,
        itemContent = {
          StationItem(stationName = it)
        }
      )
    }
  }
}

@Composable
fun StationItem(stationName: String)
{
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color.White)
  ) {
    Text(text = stationName)
  }
}