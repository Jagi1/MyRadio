package pl.sb.myradio.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import pl.sb.myradio.R

class DashboardActivity : AppCompatActivity()
{
  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContentView(
      ComposeView(this).apply {
        consumeWindowInsets = false
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