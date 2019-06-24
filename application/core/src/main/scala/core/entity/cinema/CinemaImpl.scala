package core.entity.cinema

import java.time.LocalDateTime

case class CinemaImpl(identifier: CinemaId,
                      cinemaName: String,
                      address: String,
                      ggMap: String,
                      phoneNumber: Int,
                      idIndex: Int,
                      crawledAt: Option[LocalDateTime],
                      override val createdAt: Option[LocalDateTime],
                      override val updatedAt: Option[LocalDateTime]) extends Cinema {
}
