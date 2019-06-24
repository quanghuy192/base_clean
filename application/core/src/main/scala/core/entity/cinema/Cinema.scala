package core.entity.cinema

import java.time.LocalDateTime
import base.models.Entity

trait Cinema extends Entity[CinemaId] with CinemaField

object Cinema {
  def apply(identifier: CinemaId, cinemaName: String,
            address: String,
            ggMap: String,
            phoneNumber: Int,
            idIndex: Int, crawledAt: Option[LocalDateTime],
            createdAt: Option[LocalDateTime],
            updatedAt: Option[LocalDateTime]): Cinema = CinemaImpl(identifier, cinemaName, address, ggMap, phoneNumber, idIndex, crawledAt, createdAt, updatedAt)
}
