package usecase.movie

import core.entity.Movie

trait GetMovieInterface {
  def getAll(isCurrent: Boolean): List[Movie]
}
