package pl.sb.myradio.view.dashboard

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioManager
import android.os.Bundle
import android.os.IBinder
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.sb.myradio.model.Volume
import pl.sb.myradio.service.RadioService
import pl.sb.myradio.view.theme.DarkColorPalette
import pl.sb.myradio.view.theme.LightColorPalette
import pl.sb.myradio.view.theme.Shapes
import pl.sb.myradio.view.theme.Typography
import pl.sb.myradio.viewModel.dashboard.DashboardViewModel

class DashboardActivity : AppCompatActivity()
{
  internal val viewModel: DashboardViewModel by viewModel()
  internal var mRadioService: RadioService? = null
  internal var mRadioServiceBound: Boolean = false
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

    setContent {
      MaterialTheme(
        colors = if (isSystemInDarkTheme()) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes,
      ) {
        ObserveChosenStation(viewModel = viewModel)
        ObservePlayerState(viewModel = viewModel)
        ObserveMute(viewModel = viewModel)
        ObserveVolume(viewModel = viewModel)
        DashboardScreen(viewModel = viewModel)
      }
    }

    initializeCurrentVolume()
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

  private fun initializeCurrentVolume()
  {
    val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
    val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    val volume = Volume(
      currentPercent = (currentVolume / maxVolume * 100).toFloat(),
      max = maxVolume
    )
    viewModel.initializeCurrentVolume(volume)
  }
}