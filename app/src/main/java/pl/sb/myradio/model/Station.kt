package pl.sb.myradio.model

import pl.sb.myradio.base.Consumable

data class Station(
  val name: String,
  val url: String,
  override val isConsumed: Boolean = false,
  ) : Consumable
