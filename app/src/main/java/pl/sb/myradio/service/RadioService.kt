package pl.sb.myradio.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.media3.common.Player
import androidx.media3.common.Player.COMMAND_PLAY_PAUSE
import androidx.media3.common.Player.COMMAND_STOP
import androidx.media3.exoplayer.ExoPlayer
import pl.sb.myradio.util.getDefaultHttpDataSourceFactory
import pl.sb.myradio.util.getProgressiveMediaSource
import pl.sb.myradio.util.getUserAgent

class RadioService : Service()
{
  private val binder = LocalBinder()
  private lateinit var player: ExoPlayer

  override fun onBind(p0: Intent?): IBinder
  {
    createPlayer()
    return binder
  }

  @SuppressLint("UnsafeOptInUsageError")
  fun initializePlayer(stationUrl: String)
  {
    val userAgent = getUserAgent(applicationContext)
    val dataSourceFactory = getDefaultHttpDataSourceFactory(userAgent)
    val mediaSource = getProgressiveMediaSource(stationUrl, dataSourceFactory)
    if (this::player.isInitialized.not())
    {
      createPlayer()
    }
    player.apply {
      setMediaSource(mediaSource)
      prepare()
      addListener(object : Player.Listener
      {
      })
      playWhenReady = true
    }
  }

  fun pausePlayer()
  {
    if (this::player.isInitialized) {
      if (player.availableCommands.contains(COMMAND_PLAY_PAUSE)) {
        if (player.isPlaying) {
          player.pause()
        }
      }
    }
  }

  fun startPlayer()
  {
    if (this::player.isInitialized) {
      if (player.availableCommands.contains(COMMAND_PLAY_PAUSE)) {
        if (player.isPlaying.not()) {
          player.play()
        }
      }
    }
  }

  fun stopPlayer()
  {
    if (this::player.isInitialized) {
      if (player.availableCommands.contains(COMMAND_STOP)) {
        if (player.isPlaying) {
          player.stop()
        }
      }
    }
  }

  private fun createPlayer()
  {
    player = ExoPlayer
      .Builder(this)
      .build()
  }

  inner class LocalBinder : Binder()
  {
    fun getService(): RadioService = this@RadioService
  }
}