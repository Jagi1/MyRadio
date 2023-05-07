package pl.sb.myradio.model

import pl.sb.myradio.base.Consumable

data class IsSoundMuted(
  val muted: Boolean,
  override val isConsumed: Boolean = false
) : Consumable
