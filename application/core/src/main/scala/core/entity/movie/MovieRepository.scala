package core.entity.movie

import base.repository.BasicFeatureRepository

trait MovieRepository extends BasicFeatureRepository[MovieId, MovieField, Movie]{
  def storeAll(movies: List[Movie])
}
