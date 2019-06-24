package base.models

import java.time.LocalDateTime

trait RecordTimestamp {
  val createdAt: Option[LocalDateTime] = None
  val updatedAt: Option[LocalDateTime] = None
}

