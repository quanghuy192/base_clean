package core.entity.cinema

import base.repository.BasicFeatureRepository

trait CinemaRepository extends BasicFeatureRepository[CinemaId, CinemaField, Cinema]
