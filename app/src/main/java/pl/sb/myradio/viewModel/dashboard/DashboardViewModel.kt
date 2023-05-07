package pl.sb.myradio.viewModel.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.sb.myradio.base.Consumable
import pl.sb.myradio.model.IsSoundMuted
import pl.sb.myradio.model.Station
import pl.sb.myradio.model.Volume
import pl.sb.myradio.util.getJsonDataFromAsset

class DashboardViewModel(
  application: Application,
  private val gson: Gson
) : AndroidViewModel(application)
{
  private val _stations = MutableStateFlow(listOf<Station>())
  val stations = _stations.asStateFlow()
  private val _chosenStation = MutableStateFlow<Station?>(null)
  val chosenStation = _chosenStation.asStateFlow()
  private val _playerState = MutableStateFlow<PlayerState>(PlayerState.Initial(true))
  val playerState = _playerState.asStateFlow()
  private val _newStationVisibilityState = MutableStateFlow(false)
  val newStationVisibilityState = _newStationVisibilityState.asStateFlow()
  private val _isSoundMuted = MutableStateFlow(IsSoundMuted(muted = false, isConsumed = true))
  val isSoundMuted = _isSoundMuted.asStateFlow()
  private val _soundVolume = MutableStateFlow<Volume?>(null)
  val soundVolume = _soundVolume.asStateFlow()

  init
  {
    getStations()
  }

  private fun getStations() {
    // TODO: Create room database
    viewModelScope.launch {
      _stations.update {
        val jsonFileString = getJsonDataFromAsset(getApplication(), "stations.json")
        val listStationType = object: TypeToken<List<Station>>() {}.type
        gson.fromJson(jsonFileString, listStationType)
      }
    }
  }

  fun initializeCurrentVolume(newVolume: Volume) {
    _soundVolume.update { newVolume }
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
      is UiEvents.MuteSoundButtonClicked -> {
        _isSoundMuted.update { it.copy(muted = it.muted.not(), isConsumed = false) }
      }
      is UiEvents.VolumeSliderSwiped -> {
        _soundVolume.update { it?.copy(currentPercent = event.newVolume, isConsumed = false) }
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

  fun consumeIsSoundMuted()
  {
    _isSoundMuted.update { it.copy(isConsumed = true) }
  }

  fun consumeSoundVolume()
  {
    _soundVolume.update { it?.copy(isConsumed = true) }
  }

  sealed interface UiEvents {

    data class StationClicked(val station: Station) : UiEvents
    object PlayButtonClicked : UiEvents
    object PauseButtonClicked : UiEvents
    object StopButtonClicked : UiEvents
    object AddNewStationClicked : UiEvents
    object AddNewStationBackPressed : UiEvents
    object MuteSoundButtonClicked : UiEvents
    data class VolumeSliderSwiped(val newVolume: Float) : UiEvents
  }

  sealed class PlayerState : Consumable {
    data class Playing(override val isConsumed: Boolean = false) : PlayerState()
    data class Paused(override val isConsumed: Boolean = false) : PlayerState()
    data class Stopped(override val isConsumed: Boolean = false) : PlayerState()
    data class Initial(override val isConsumed: Boolean = false) : PlayerState()
  }
}