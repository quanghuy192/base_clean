package network.movie.db

import java.time.LocalDateTime

import base.models.Record

case class MovieRecord(id: Long,
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
                       override val createdAt: Option[LocalDateTime],
                       override val updatedAt: Option[LocalDateTime]) extends Record
