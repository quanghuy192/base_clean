package base.repository

trait BasicFeatureRepository[Identifier <: base.models.Identifier[_], Fields <: base.models.Fields, Entity <: base.models.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity]
