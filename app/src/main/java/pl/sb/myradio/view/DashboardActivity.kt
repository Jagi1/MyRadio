package pl.sb.myradio.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.NavHostFragment
import pl.sb.myradio.R
import pl.sb.myradio.viewModel.DashboardViewModel

class DashboardActivity : AppCompatActivity()
{
  private val viewModel: DashboardViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)

    setContentView(
      ComposeView(this).apply {
        setContent {
          DashboardScreen(viewModel)
        }
      }
    )
  }

  override fun onSupportNavigateUp(): Boolean
  {
    return super.onSupportNavigateUp()
  }

  private fun getNavController() =
    (supportFragmentManager
      .findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
      .navController
}