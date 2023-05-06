package pl.sb.myradio.view.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import pl.sb.myradio.view.theme.Grey1
import pl.sb.myradio.view.theme.Grey2
import pl.sb.myradio.view.theme.Grey3

@Composable
fun BlurryCard(
  modifier: Modifier = Modifier,
  shape: Shape = RoundedCornerShape(16.dp),
  content: @Composable (BoxScope) -> Unit = { }
)
{
  Card(
    modifier = modifier,
    backgroundColor = Color(0xFFFFFF),
    border = BorderStroke(1.dp, color = Color(0x25FFFFFF)),
    elevation = 0.dp,
    shape = shape
  ) {
    Box(
      modifier = Modifier
        .alpha(1f)
        .blur(
          radius = 28.dp,
          edgeTreatment = BlurredEdgeTreatment.Unbounded
        )
        .background(
          Brush.radialGradient(
            colors = listOf(Grey1, Grey2, Grey3),
            radius = 2200f,
            center = Offset.Infinite
          )
        )
    ) {
      content(this)
    }
  }
}