package base.repository

import scala.util.Try

trait Repository[Identifier <: base.models.Identifier[_], Fields <: base.models.Fields, Entity <: base.models.Entity[Identifier]] {
  def resolveBy(id: Identifier)(implicit ctx: IOContext = IOContext): Try[Entity]

  def resolveAll()(implicit ctx: IOContext = IOContext): Try[Seq[Entity]]

  def store(fields: Fields)(implicit ctx: IOContext = IOContext): Try[Identifier]

  def update(entity: Entity)(implicit ctx: IOContext = IOContext): Try[Unit]

  def deleteBy(id: Identifier)(implicit ctx: IOContext = IOContext): Try[Unit]
}
