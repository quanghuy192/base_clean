package network.movie

import core.entity.Movie
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements
import usecase.movie.GetMovieInterface

import scala.collection.mutable.ListBuffer

class GetMovieByCrawl extends GetMovieInterface {

  // Link source
  private val CURRENT_MOVIE_URL: String = "https://www.betacineplex.vn/phim.htm"
  private val NEW_MOVIE_URL: String = "https://www.betacineplex.vn/phim.htm#tab-2"
//  private val CINEMA_URL: String = "https://www.cgv.vn/default/cinox/site/"

  private var document: Document = _
  private var url: String = _
  private val BLANK = ""

  def setUrlSource(url: String) = {
    this.url = url
    document = Jsoup.connect(url).userAgent("Mozilla").get()
  }

  def getMovieList(isCurrent: Boolean): List[Movie] = {
    val movieList: ListBuffer[Movie] = new ListBuffer[Movie]
    if (isCurrent) {
      setUrlSource(CURRENT_MOVIE_URL)
    } else {
      setUrlSource(NEW_MOVIE_URL)
    }

    val elementItems: Elements = document.select("div[class=row]")

    elementItems.forEach { e: Element =>
      val general: Elements = e.select("div[class=pi-img-wrapper]")
      val detail: Elements = e.select("div[class=film-info film-xs-info]")

      if(!general.isEmpty && !detail.isEmpty){
        if(general.select("div[class=pi-img-wrapper]").size() > 0){
          val img: String = general.select("img").attr("src")
          val trailer: String = general.select("a").attr("onclick")

          if(detail.select("h3").size == 1){
            val title: String = detail.select("h3").text()
            val elements: Elements = detail.select("li")
            val _type = elements.get(0).text
            val format = elements.get(1).text
            val time = elements.get(2).text

            movieList += Movie(title, _type, img, trailer, time, BLANK, BLANK, BLANK, BLANK, BLANK, format)
          }
        }
      }
    }
    movieList.toList
  }
  override def getAll(isCurrent: Boolean): List[Movie] = getMovieList(isCurrent)
}