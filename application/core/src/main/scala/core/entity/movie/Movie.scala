package core.entity.movie

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Movie(title: String, _type: String, icon: String, linkTrailer: String, time: String,
                 director: String, actors: String, imdbPoint: String, dayStart: String, content: String, format: String)

object Movie {

  implicit val movieWrites = new Writes[Movie] {
    def writes(movie: Movie) = Json.obj(
      "title" -> movie.title,
      "_type" -> movie._type,
      "icon" -> movie.icon,
      "linkTrailer" -> movie.linkTrailer,
      "time" -> movie.time,
      "director" -> movie.director,
      "actors" -> movie.actors,
      "imdb" -> movie.imdbPoint,
      "dayStart" -> movie.dayStart,
      "content" -> movie.content,
      "format" -> movie.format
    )
  }

  implicit val movieReads: Reads[Movie] = (
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
      (__ \ "format").read[String]
    ) (Movie.apply _)

}

// Not use
//private short likes
//private short views
