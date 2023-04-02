package pl.sb.myradio.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel : ViewModel()
{
  private val _stations = MutableStateFlow(listOf<String>())
  val stations = _stations.asStateFlow()

  fun getStations() {
    // TODO: Create room database
    _stations.value = listOf("Station1", "Station2", "Station3")
  }
}