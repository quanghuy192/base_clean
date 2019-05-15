package usecase.movie

import core.entity.movie.Movie
import crawl.MovieCrawl
import javax.inject.Inject
import repository.MovieRepository

class MovieOperation @Inject()(movieRepository: MovieRepository, movieCrawl: MovieCrawl) {
  def getAll(isCurrent: Boolean): List[Movie] = {
    val movieList = movieRepository.getAll
    if (movieList.nonEmpty) {
      movieList
    } else {
      val result = movieCrawl.getAll(isCurrent)
      if (result.nonEmpty) movieRepository.storeAll(result)
      result
    }
  }
}
