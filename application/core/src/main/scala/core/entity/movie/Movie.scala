package core.entity.movie

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale

import base.models.Entity
import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.util.Try

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

  //    implicit val movieWrites: Writes[Movie] = (
  //      (__ \ "id").write[String] and
  //        (__ \ "title").write[String] and
  //        (__ \ "_type").write[String] and
  //        (__ \ "icon").write[String] and
  //        (__ \ "linkTrailer").write[String] and
  //        (__ \ "time").write[String] and
  //        (__ \ "director").write[String] and
  //        (__ \ "actors").write[String] and
  //        (__ \ "imdb").write[String] and
  //        (__ \ "dayStart").write[String] and
  //        (__ \ "content").write[String] and
  //        (__ \ "format").write[String] and
  //        (__ \ "crawledAt").write[Option[LocalDateTime]] and
  //        (__ \ "createdAt").write[Option[LocalDateTime]] and
  //        (__ \ "updatedAt").write[Option[LocalDateTime]]
  //      ) (unlift(Movie.unapply))

  implicit val movieWrites = new Writes[Movie] {
    def writes(movie: Movie) = Json.obj(
      "id" -> movie.identifier.value,
      "title" -> movie.title,
      "mType" -> movie.mType,
      "icon" -> movie.icon,
      "linkTrailer" -> movie.linkTrailer,
      "time" -> movie.time,
      "director" -> movie.director,
      "actors" -> movie.actors,
      "imdb" -> movie.imdbPoint,
      "dayStart" -> movie.dayStart,
      "content" -> movie.content,
      "format" -> movie.format,
      "crawledAt" -> movie.crawledAt,
      "createdAt" -> movie.createdAt,
      "updatedAt" -> movie.updatedAt
    )
  }

  implicit val readDateTime: Reads[LocalDateTime] = new Reads[LocalDateTime] {
    private val format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)

    override def reads(json: JsValue): JsResult[LocalDateTime] = json match {
      case JsString(d) => Try(format.parse(d)).map(t => JsSuccess(LocalDateTime.parse(t.toString))).getOrElse(error(json))
      case _ => error(json)
    }

    private def error(json: JsValue) = JsError(s"Unable to parse $json into a DateTime with format EEE, dd MMM yyyy HH:mm:ss z ")
  }

  implicit object IdFormat extends Format[MovieId] {
    override def reads(json: JsValue): JsResult[MovieId] = JsSuccess(MovieId(json.as[String].toLong))
    override def writes(movieId: MovieId): JsValue = JsString(movieId.value.toString)
  }

  implicit object DateFormat extends Format[Option[LocalDateTime]] {
    val format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    override def reads(json: JsValue): JsResult[Option[LocalDateTime]] = JsSuccess(Some(LocalDateTime.parse(json.as[String])))
    override def writes(date: Option[LocalDateTime]): JsValue = JsString(format.format(date.get))
  }

  implicit val movieReads: Reads[Movie] = (
    (__ \ "id").read[MovieId] and
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
