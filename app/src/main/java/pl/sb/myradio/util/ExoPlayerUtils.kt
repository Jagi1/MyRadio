package pl.sb.myradio.util

import android.annotation.SuppressLint
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource

@SuppressLint("UnsafeOptInUsageError")
fun getUserAgent(context: Context) =
  Util
    .getUserAgent(context, "MyRadio")

@SuppressLint("UnsafeOptInUsageError")
fun getDefaultHttpDataSourceFactory(userAgent: String) =
  DefaultHttpDataSource
    .Factory()
    .setUserAgent(userAgent)

@SuppressLint("UnsafeOptInUsageError")
fun getProgressiveMediaSource(url: String, httpDataSourceFactory: DefaultHttpDataSource.Factory) =
  ProgressiveMediaSource
    .Factory(httpDataSourceFactory)
    .createMediaSource(MediaItem.fromUri(url))