package pl.sb.myradio.di

import com.google.gson.Gson
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.sb.myradio.viewModel.dashboard.DashboardViewModel

val dashboardModule = module {

  viewModel {
    DashboardViewModel(
      application = androidApplication(),
      gson = Gson()
    )
  }
}