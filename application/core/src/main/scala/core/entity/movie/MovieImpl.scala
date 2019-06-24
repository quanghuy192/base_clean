package core.entity.movie

import java.time.LocalDateTime

case class MovieImpl(identifier: MovieId,
                     title: String,
                     mType: String,
                     icon: String,
                     linkTrailer: String,
                     time: String,
                     director: String,
                     actors: String,
                     imdbPoint: String,
                     dayStart: String,
                     content: String,
                     format: String,
                     crawledAt: Option[LocalDateTime],
                     override val createdAt: Option[LocalDateTime],
                     override val updatedAt: Option[LocalDateTime]) extends Movie {
}
