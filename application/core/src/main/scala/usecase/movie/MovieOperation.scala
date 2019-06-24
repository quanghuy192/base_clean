package usecase.movie

import core.entity.movie.{Movie, MovieRepository}
import crawl.MovieCrawl
import javax.inject.Inject

class MovieOperation @Inject()(movieRepository: MovieRepository, movieCrawl: MovieCrawl) {
  def getAll(isCurrent: Boolean): List[Movie] = {

    val movieList: Seq[Movie] = movieRepository.resolveAll().recover {
      case e: Exception => throw e
    } get

    if (movieList.toList.nonEmpty) {
      movieList.toList
    } else {
      val result = movieCrawl.getAll(isCurrent)
      if (result.nonEmpty) movieRepository.storeAll(result)
      result
    }
  }
}
