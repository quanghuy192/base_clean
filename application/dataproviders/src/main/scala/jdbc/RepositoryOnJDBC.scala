package jdbc

import base.exceptions.{EntityDuplicateException, EntityNotFoundException, OptimisticLockException}
import base.repository.{BasicFeatureRepository, IOContext, Repository}
import base.models.{Entity => EntityBase, Fields => FieldsBase, Identifier => IdentifierBase, Record => RecordBase}
import com.mysql.jdbc.MysqlErrorNumbers
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
import scalikejdbc._

import scala.util.{Failure, Success, Try}

trait RepositoryOnJDBC[Identifier <: IdentifierBase[_], Fields <: FieldsBase, Entity <: EntityBase[Identifier], RecordId, Record <: RecordBase]
  extends Repository[Identifier, Fields, Entity] {

  type DAO <: CRUDMapperWithId[RecordId, Fields, Record]
  protected[this] val dao: DAO

  protected def convertToRecordId(identifier: Identifier): RecordId

  protected def getDBSession(implicit ctx: IOContext): Try[DBSession] = {
    ctx match {
      case IOContextOnJDBC(session) => Success(session)
      case _ =>
        Success(AutoSession)
    }
  }

  protected def withDBSession[T](func: DBSession => T)(implicit ctx: IOContext = IOContext): Try[T] = {
    getDBSession.map(func).recoverWith {
      case e: MySQLIntegrityConstraintViolationException =>
        e.getErrorCode match {
          case MysqlErrorNumbers.ER_DUP_ENTRY => Failure(new EntityDuplicateException(e.getMessage, e))
          case _ => Failure(e)
        }
      case e: Exception => throw e
    }
  }
}

trait BasicFeatureRepositoryOnJDBC[Identifier <: IdentifierBase[_], Fields <: FieldsBase, Entity <: EntityBase[Identifier], RecordId, Record <: RecordBase]
  extends RepositoryOnJDBC[Identifier, Fields, Entity, RecordId, Record] with BasicFeatureRepository[Identifier, Fields, Entity] {

  protected def convertToEntity(record: Record): Entity

  protected def fieldsFromNamedValues(fields: Fields): Seq[(Symbol, Any)]

  def convertToIdentifier(id: RecordId): Identifier

  def store(fields: Fields)(implicit ctx: IOContext = IOContext): Try[Identifier] = withDBSession { implicit session =>
    val createAttributes = fieldsFromNamedValues(fields)
    val id = dao.createWithAttributes(createAttributes: _*)
    convertToIdentifier(id)
  }

  def resolveBy(identifier: Identifier)(implicit ctx: IOContext = IOContext): Try[Entity] = withDBSession { implicit session =>
    val entity = dao.findById(convertToRecordId(identifier)).map(convertToEntity)
    entity.getOrElse(throw new EntityNotFoundException(s"model = ${dao.tableName}, id = $identifier"))
  }

  def resolveAll()(implicit ctx: IOContext = IOContext): Try[Seq[Entity]] = withDBSession { implicit session =>
    dao.findAll().map(convertToEntity)
  }

  override def update(entity: Entity)(implicit ctx: IOContext = IOContext): Try[Unit] = withDBSession { implicit session =>
    val createAttributes = fieldsFromNamedValues(entity.asInstanceOf[Fields])
    implicit val e = ParameterBinderFactory.asisParameterBinderFactory
    val updatedCount = dao.updateBy(sqls.eq(dao.column.id, entity.identifier.value.asInstanceOf[Any]).and(sqls.eq(dao.column.field("updatedAt"), entity.updatedAt)))
      .withAttributes(createAttributes: _*)
    if (updatedCount == 1) {
      ()
    } else {
      throw new OptimisticLockException(s"model = ${dao.tableName}, id = $entity.identifier")
    }
  }

  def deleteBy(identifier: Identifier)(implicit ctx: IOContext = IOContext): Try[Unit] = withDBSession { implicit session =>
    val deletedCount = dao.deleteById(convertToRecordId(identifier))

    if (deletedCount == 1) {
      ()
    } else {
      throw new EntityNotFoundException(s"model = ${dao.tableName}, id = $identifier")
    }
  }
}

