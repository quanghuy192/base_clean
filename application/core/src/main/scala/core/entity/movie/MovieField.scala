package core.entity.movie

import java.time.LocalDateTime

import base.models.{Fields, RecordTimestamp}

trait MovieField extends Fields with RecordTimestamp {
  val title: String
  val mType: String
  val icon: String
  val linkTrailer: String
  val time: String
  val director: String
  val actors: String
  val imdbPoint: String
  val dayStart: String
  val content: String
  val format: String
  val crawledAt: Option[LocalDateTime]
}
