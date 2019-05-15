package inject

import com.google.inject.AbstractModule
import crawl.MovieCrawl
import network.movie.crawl.GetMovieByCrawl

class Module extends AbstractModule {
  override def configure() = {
    bind(classOf[MovieCrawl]).toInstance(new GetMovieByCrawl)
  }
}
