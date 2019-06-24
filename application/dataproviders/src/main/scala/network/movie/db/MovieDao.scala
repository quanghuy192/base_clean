package network.movie.db

import core.entity.movie.MovieField
import jdbc.CRUDMapperWithId
import scalikejdbc.interpolation.SQLSyntax
import scalikejdbc.{ DBSession, WrappedResultSet, _ }

class MovieDao extends CRUDMapperWithId[Long, MovieField, MovieRecord] {

  override def useAutoIncrementPrimaryKey: Boolean = true

  override lazy val tableName = "movie"
  override lazy val defaultAlias = createAlias("m")

  /**
    * idToRawValue
    *
    * @param id
    * @return
    */
  override def idToRawValue(id: Long) = id

  /**
    * rawValueToId
    *
    * @param value
    * @return
    */
  override def rawValueToId(value: Any) = value.asInstanceOf[Long]

  /**
    * toNamedValues
    *
    * @param entity
    * @return
    */
  override def toNamedValues(entity: MovieRecord): Seq[(Symbol, Any)] = Seq(
    'id -> entity.id,
    'title -> entity.title,
    'm_type -> entity.mType,
    'icon -> entity.icon,
    'link_trailer -> entity.linkTrailer,
    'time -> entity.time,
    'director -> entity.director,
    'actors -> entity.actors,
    'imdb_point -> entity.imdbPoint,
    'day_start -> entity.dayStart,
    'content -> entity.content,
    'format -> entity.format,
    'created_at -> entity.createdAt,
    'updated_at -> entity.updatedAt)

  /**
    * extract
    *
    * @param rs
    * @param n
    * @return
    */
  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[MovieRecord]) = {
    MovieRecord(
      id = rs.get(n.id),
      title = rs.get(n.title),
      mType = rs.get(n.mType),
      icon = rs.get(n.icon),
      linkTrailer = rs.get(n.linkTrailer),
      time = rs.get(n.time),
      director = rs.get(n.director),
      actors = rs.get(n.actors),
      imdbPoint = rs.get(n.imdbPoint),
      dayStart = rs.get(n.dayStart),
      content = rs.get(n.content),
      format = rs.get(n.format),
      createdAt = rs.get(n.createdAt),
      updatedAt = rs.get(n.updatedAt))
  }

  def bulkCreateOrUpdate(updateColumns: Seq[SQLSyntax],
                          fields: Seq[MovieField],
                          duplicateKeys: Seq[SQLSyntax],
                          toValue: MovieField => Seq[Any])(implicit s: DBSession): Int = {
    if (fields.isEmpty) {
      0
    } else {
      withSQL {
        insertInto(this)
          .columns(updateColumns: _*)
          .append(BulkQuery.multiValuesPlaceholders(fields.size, updateColumns.size))
          .append(BulkQuery.onDuplicateKeyUpdateSyntax(duplicateKeys))
      }.bind(
        fields.flatMap(toValue): _*).executeUpdate().apply()(s)
    }
  }
}
