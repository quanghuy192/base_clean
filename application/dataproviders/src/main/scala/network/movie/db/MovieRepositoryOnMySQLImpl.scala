package network.movie.db

import core.entity.movie.{Movie, MovieField, MovieId, MovieRepository}
import jdbc.BasicFeatureRepositoryOnJDBC
import scalikejdbc.{AutoSession, sqls}

class MovieRepositoryOnMySQLImpl(override protected[this] val dao: MovieDao = new MovieDao)
  extends MovieRepository with BasicFeatureRepositoryOnJDBC[MovieId, MovieField, Movie, Long, MovieRecord] {

  override type DAO = MovieDao

  override protected def convertToEntity(record: MovieRecord) = Movie(MovieId(record.id),
    record.title, record.mType, record.icon, record.linkTrailer, record.time, record.director,
    record.actors, record.imdbPoint, record.dayStart, record.content, record.format, record.createdAt, record.createdAt, record.updatedAt)

  override protected def fieldsFromNamedValues(fields: MovieField): Seq[(Symbol, Any)] = Seq(
    'title -> fields.title,
    'm_type -> fields.mType,
    'icon -> fields.icon,
    'link_trailer -> fields.linkTrailer,
    'time -> fields.time,
    'director -> fields.director,
    'actors -> fields.actors,
    'imdb_point -> fields.imdbPoint,
    'day_start -> fields.dayStart,
    'content -> fields.content,
    'format -> fields.format,
    'created_at -> fields.createdAt,
    'updated_at -> fields.updatedAt)

  override def convertToIdentifier(id: Long): MovieId = MovieId(id)

  override protected def convertToRecordId(identifier: MovieId): Long = identifier.value

  protected def toValues(fields: MovieField): Seq[Any] = fieldsFromNamedValues(fields).map(v => v._2)

  override def storeAll(movies: List[Movie]) = {
    withDBSession { implicit session =>
      dao.bulkCreateOrUpdate(
        Seq(
          dao.column.title,
          dao.column.mType,
          dao.column.icon,
          dao.column.linkTrailer,
          dao.column.time,
          dao.column.director,
          dao.column.actors,
          dao.column.imdbPoint,
          dao.column.dayStart,
          dao.column.content,
          dao.column.format,
          dao.column.createdAt,
          dao.column.updatedAt),
        movies,
        Seq(
          dao.column.campaignId,
          dao.column.adsGroupId,
          dao.column.keyword,
          dao.column.keywordHash,
          dao.column.idApi,
          dao.column.platform,
          dao.column.condensedKeyword,
          dao.column.condensedKeywordHash,
          dao.column.keywordType,
          dao.column.matchType,
          dao.column.createdAt,
          dao.column.updatedAt),
        toValues)
    }
  }
}
