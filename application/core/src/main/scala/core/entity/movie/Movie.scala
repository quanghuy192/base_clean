package core.entity.movie

import java.time.LocalDateTime

import base.models.Entity
import play.api.libs.functional.syntax._
import play.api.libs.json._

trait Movie extends Entity[MovieId] with MovieField

object Movie {
  def apply(identifier: MovieId, title: String,
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
            createdAt: Option[LocalDateTime],
            updatedAt: Option[LocalDateTime]): Movie =
    MovieImpl(identifier, title, mType, icon, linkTrailer, time, director, actors, imdbPoint, dayStart, content, format, crawledAt, createdAt, updatedAt)

  implicit val movieWrites: Writes[Movie] = (
    (__ \ "id").write[String] and
      (__ \ "title").write[String] and
      (__ \ "_type").write[String] and
      (__ \ "icon").write[String] and
      (__ \ "linkTrailer").write[String] and
      (__ \ "time").write[String] and
      (__ \ "director").write[String] and
      (__ \ "actors").write[String] and
      (__ \ "imdb").write[String] and
      (__ \ "dayStart").write[String] and
      (__ \ "content").write[String] and
      (__ \ "format").write[String] and
      (__ \ "crawledAt").write[Option[LocalDateTime]] and
      (__ \ "createdAt").write[Option[LocalDateTime]] and
      (__ \ "updatedAt").write[Option[LocalDateTime]]
    ) (Movie)

  implicit val movieReads: Reads[Movie] = (
    (__ \ "id").read[String] and
      (__ \ "title").read[String] and
      (__ \ "_type").read[String] and
      (__ \ "icon").read[String] and
      (__ \ "linkTrailer").read[String] and
      (__ \ "time").read[String] and
      (__ \ "director").read[String] and
      (__ \ "actors").read[String] and
      (__ \ "imdb").read[String] and
      (__ \ "dayStart").read[String] and
      (__ \ "content").read[String] and
      (__ \ "format").read[String] and
      (__ \ "crawledAt").read[Option[LocalDateTime]] and
      (__ \ "createdAt").read[Option[LocalDateTime]] and
      (__ \ "updatedAt").read[Option[LocalDateTime]]
    ) (Movie.apply _)
}

// Not use
//private short likes
//private short views
