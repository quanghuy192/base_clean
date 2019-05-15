package crawl

import core.entity.movie.Movie

trait MovieCrawl {
  def getAll(isCurrent: Boolean): List[Movie]
}
