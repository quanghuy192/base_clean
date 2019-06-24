package jdbc

import scalikejdbc._
import scalikejdbc.interpolation.SQLSyntax
import skinny.orm.SkinnyCRUDMapperWithId

trait CRUDMapperWithId[ID, Fields, Entity] extends SkinnyCRUDMapperWithId[ID, Entity] {
  override def useAutoIncrementPrimaryKey: Boolean = false

  def toNamedValues(entity: Entity): Seq[(Symbol, Any)]

  object BulkQuery {

    def multiValuesPlaceholders(recordSize: Int, syntaxSize: Int): SQLSyntax = {
      val oneRecordBlock = sqls.roundBracket(
        sqls.csv(Seq.fill(syntaxSize)(sqls.?): _*))
      sqls" values ${sqls.csv(Seq.fill(recordSize)(oneRecordBlock): _*)} "
    }

    def onDuplicateKeyUpdateSyntax(duplicateKeys: Seq[SQLSyntax]): SQLSyntax = {
      sqls" on duplicate key update ".append(
        sqls.csv(
          duplicateKeys.map(k => sqls"$k = values($k)"): _*))
    }
  }
}
