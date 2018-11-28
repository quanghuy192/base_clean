package inject

import com.google.inject.AbstractModule
import network.movie.GetMovieByCrawl
import usecase.movie.GetMovieInterface

class Module extends AbstractModule {
  override def configure() = {
    bind(classOf[GetMovieInterface]).toInstance(new GetMovieByCrawl)
  }
}
