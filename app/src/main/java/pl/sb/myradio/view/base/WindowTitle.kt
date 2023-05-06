package pl.sb.myradio.view.base

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun BoxScope.WindowTitle(text: String) {
  Text(
    text = text,
    modifier = Modifier.align(Alignment.TopStart),
    style = TextStyle(
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold
    )
  )
}

@Composable
fun ColumnScope.WindowTitle(text: String) {
  Text(
    text = text,
    style = TextStyle(
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold
    )
  )
}