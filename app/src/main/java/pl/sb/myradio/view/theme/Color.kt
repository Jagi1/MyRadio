package pl.sb.myradio.view.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

// for blur purpose
val Glass1 = Color(0x22FFFFFF)
val Glass2 = Color(0x0EFFFFFF)
val Glass3 = Color(0xAFFFFFFF)

val Purple200 = Color(0xFFCE93D8)
val Purple500 = Color(0xFF9C27B0)
val Purple700 = Color(0xFF7B1FA2)
val Teal200 = Color(0xFF80CBC4)

val LightColorPalette = lightColors(
  primary = Purple500,
  primaryVariant = Purple700,
  secondary = Teal200
)

val DarkColorPalette = darkColors(
  primary = Purple200,
  primaryVariant = Purple700,
  secondary = Teal200
)