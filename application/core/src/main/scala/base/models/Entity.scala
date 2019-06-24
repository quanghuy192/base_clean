package base.models

trait Entity[ID <: Identifier[_]] extends RecordTimestamp {
  val identifier: ID
}

