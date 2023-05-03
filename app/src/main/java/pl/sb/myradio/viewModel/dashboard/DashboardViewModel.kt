package pl.sb.myradio.viewModel.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.sb.myradio.base.Consumable
import pl.sb.myradio.model.Station

class DashboardViewModel : ViewModel()
{
  private val _stations = MutableStateFlow(listOf<Station>())
  val stations = _stations.asStateFlow()
  private val _chosenStation = MutableStateFlow<Station?>(null)
  val chosenStation = _chosenStation.asStateFlow()
  private val _playerState = MutableStateFlow<PlayerState>(PlayerState.Initial(true))
  val playerState = _playerState.asStateFlow()
  private val _newStationVisibilityState = MutableStateFlow(false)
  val newStationVisibilityState = _newStationVisibilityState.asStateFlow()

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
      is UiEvents.StationClicked     ->
      {
        _chosenStation.update { event.station.copy(isConsumed = false) }
      }
      is UiEvents.PauseButtonClicked ->
      {
        _playerState.update { PlayerState.Paused(false) }
      }
      is UiEvents.PlayButtonClicked  ->
      {
        _playerState.update { PlayerState.Playing(false) }
      }
      is UiEvents.StopButtonClicked  ->
      {
        _playerState.update { PlayerState.Stopped(false) }
      }
      is UiEvents.AddNewStationClicked  ->
      {
        _newStationVisibilityState.update { true }
      }
      is UiEvents.AddNewStationBackPressed -> {
        _newStationVisibilityState.update { false }
      }
    }
  }

  fun consumeChosenStation()
  {
    _chosenStation.update { it?.copy(isConsumed = true) }
  }

  fun consumePlayerState()
  {
    _playerState.update {
      when (it) {
        is PlayerState.Initial -> it.copy(true)
        is PlayerState.Paused  -> it.copy(true)
        is PlayerState.Playing -> it.copy(true)
        is PlayerState.Stopped -> it.copy(true)
      }
    }
  }

  sealed interface UiEvents {

    data class StationClicked(val station: Station) : UiEvents
    object PlayButtonClicked : UiEvents
    object PauseButtonClicked : UiEvents
    object StopButtonClicked : UiEvents
    object AddNewStationClicked : UiEvents
    object AddNewStationBackPressed : UiEvents
  }

  sealed class PlayerState : Consumable {
    data class Playing(override val isConsumed: Boolean = false) : PlayerState()
    data class Paused(override val isConsumed: Boolean = false) : PlayerState()
    data class Stopped(override val isConsumed: Boolean = false) : PlayerState()
    data class Initial(override val isConsumed: Boolean = false) : PlayerState()
  }
}