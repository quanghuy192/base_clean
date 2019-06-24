package core.entity.cinema

import java.time.LocalDateTime
import base.models.{Fields, RecordTimestamp}

trait CinemaField extends Fields with RecordTimestamp {
  val cinemaName: String
  val address: String
  val ggMap: String
  val phoneNumber: Int
  val idIndex: Int
  val crawledAt: Option[LocalDateTime]
}
