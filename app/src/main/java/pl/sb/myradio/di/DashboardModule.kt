package pl.sb.myradio.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import pl.sb.myradio.viewModel.dashboard.DashboardViewModel

val dashboardModule = module {

  viewModelOf(::DashboardViewModel)
}