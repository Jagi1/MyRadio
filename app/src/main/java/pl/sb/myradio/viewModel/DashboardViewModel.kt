package pl.sb.myradio.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.sb.myradio.model.Station

class DashboardViewModel : ViewModel()
{
  private val _stations = MutableStateFlow(listOf<Station>())
  val stations = _stations.asStateFlow()
  private val _chosenStation = MutableStateFlow<Station?>(null)
  val chosenStation = _chosenStation.asStateFlow()

  init
  {
    getStations()
  }

  private fun getStations() {
    // TODO: Create room database
    _stations.value = listOf(
      Station("Station 1", "http://airspectrum.cdnstream1.com:8114/1648_128"),
      Station("Station 2", "url 2"),
      Station("Station 3", "url 3")
    )
  }

  fun resolveUiEvent(event: UiEvents) {
    when (event) {
      is UiEvents.StationClicked ->
      {
        _chosenStation.update { event.station }
      }
      is UiEvents.PauseButtonClicked -> TODO()
      is UiEvents.PlayButtonClicked -> TODO()
      is UiEvents.StopButtonClicked -> TODO()
    }
  }

  sealed interface UiEvents {

    data class StationClicked(val station: Station) : UiEvents
    object PlayButtonClicked : UiEvents
    object PauseButtonClicked : UiEvents
    object StopButtonClicked : UiEvents
  }
}