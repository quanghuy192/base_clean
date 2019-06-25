package inject

import com.google.inject.AbstractModule
import core.entity.movie.MovieRepository
import crawl.MovieCrawl
import network.movie.crawl.GetMovieByCrawl
import network.movie.db.MovieRepositoryOnMySQLImpl

class Module extends AbstractModule {
  override def configure() = {
    bind(classOf[MovieCrawl]).toInstance(new GetMovieByCrawl)
    bind(classOf[MovieRepository]).toInstance(new MovieRepositoryOnMySQLImpl)
  }
}
