package core.entity.movie

import base.models.Identifier

case class MovieId(value: Long) extends Identifier[Long]

object MovieId {
}
