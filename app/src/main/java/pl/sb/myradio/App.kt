package pl.sb.myradio

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.sb.myradio.di.appModules

class App : Application()
{
  override fun onCreate()
  {
    super.onCreate()

    startKoin {
      androidContext(this@App)
      modules(appModules)
    }
  }
}