package pl.sb.myradio.view.dashboard

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.sb.myradio.service.RadioService
import pl.sb.myradio.viewModel.dashboard.DashboardViewModel

class DashboardActivity : AppCompatActivity()
{
  private val viewModel: DashboardViewModel by viewModel()
  private var mRadioService: RadioService? = null
  private var mRadioServiceBound: Boolean = false
  private val connection = object: ServiceConnection {
    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?)
    {
      val binder = p1 as? RadioService.LocalBinder
      mRadioService = binder?.getService()
      mRadioServiceBound = mRadioService != null
    }

    override fun onServiceDisconnected(p0: ComponentName?)
    {
      mRadioServiceBound = false
    }

  }

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    setContentView(
      ComposeView(this).apply {
        setContent {
          val chosenStation by viewModel.chosenStation.collectAsState()
          val playerState by viewModel.playerState.collectAsState()

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

          LaunchedEffect(playerState) {
            if (playerState.isConsumed.not()) {
              when (playerState) {
                is DashboardViewModel.PlayerState.Playing ->
                {
                  mRadioService?.let { service ->
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

          DashboardScreen(viewModel)
        }
      }
    )
  }

  override fun onStart()
  {
    super.onStart()
    Intent(this, RadioService::class.java).also { intent ->
      bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
  }

  override fun onStop()
  {
    super.onStop()
    unbindService(connection)
    mRadioServiceBound = false
  }

  override fun onWindowFocusChanged(hasFocus: Boolean)
  {
    super.onWindowFocusChanged(hasFocus)
    WindowCompat.getInsetsController(window, window.decorView).let { controller ->
      controller.hide(WindowInsetsCompat.Type.systemBars())
      controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
  }
}