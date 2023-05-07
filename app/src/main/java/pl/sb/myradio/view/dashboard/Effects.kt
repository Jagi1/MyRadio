package pl.sb.myradio.view.dashboard

import android.content.Context
import android.media.AudioManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import pl.sb.myradio.viewModel.dashboard.DashboardViewModel

@Composable
fun DashboardActivity.ObservePlayerState(
  viewModel: DashboardViewModel
) {
  val playerState by viewModel.playerState.collectAsState()
  LaunchedEffect(playerState) {
    if (playerState.isConsumed.not()) {
      when (playerState) {
        is DashboardViewModel.PlayerState.Playing ->
        {
          this@ObservePlayerState.mRadioService?.let { service ->
            if (mRadioServiceBound) {
              service.startPlayer()
            }
          }
        }
        is DashboardViewModel.PlayerState.Paused  ->
        {
          mRadioService?.let { service ->
            if (mRadioServiceBound) {
              service.pausePlayer()
            }
          }
        }
        is DashboardViewModel.PlayerState.Stopped ->
        {
          mRadioService?.let { service ->
            if (mRadioServiceBound) {
              service.stopPlayer()
            }
          }
        }
        is DashboardViewModel.PlayerState.Initial ->
        {
          // do nothing
        }
      }
      viewModel.consumePlayerState()
    }
  }
}

@Composable
fun DashboardActivity.ObserveChosenStation(
  viewModel: DashboardViewModel
) {
  val chosenStation by viewModel.chosenStation.collectAsState()
  LaunchedEffect(chosenStation) {
    if (chosenStation?.isConsumed?.not() == true) {
      chosenStation?.let { station ->
        mRadioService?.let { service ->
          if (mRadioServiceBound) {
            service.initializePlayer(station.url)
          }
        }
      }
      viewModel.consumeChosenStation()
    }
  }
}

@Composable
fun DashboardActivity.ObserveMute(
  viewModel: DashboardViewModel
) {
  val isSoundMuted by viewModel.isSoundMuted.collectAsState()
  val soundVolume by viewModel.soundVolume.collectAsState()
  LaunchedEffect(isSoundMuted) {
    if (isSoundMuted?.isConsumed?.not() == true) {
      soundVolume?.let { volume ->
        val newVolume = when (isSoundMuted?.muted) {
          true -> 0
          else -> volume.getCurrentValue()
        }
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0)
      }
      viewModel.consumeIsSoundMuted()
    }
  }
}

@Composable
fun DashboardActivity.ObserveVolume(
  viewModel: DashboardViewModel
) {
  val soundVolume by viewModel.soundVolume.collectAsState()
  LaunchedEffect(soundVolume) {
    if (soundVolume?.isConsumed?.not() == true) {
      soundVolume?.let { volume ->
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume.getCurrentValue(), 0)
      }
      viewModel.consumeSoundVolume()
    }
  }




}