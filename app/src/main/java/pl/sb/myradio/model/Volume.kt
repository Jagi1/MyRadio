package pl.sb.myradio.model

import pl.sb.myradio.base.Consumable

data class Volume(
  val currentPercent: Float,
  val max: Int,
  override val isConsumed: Boolean = false,
) : Consumable
{
  fun getCurrentValue() = (currentPercent * max / 100).toInt()
}