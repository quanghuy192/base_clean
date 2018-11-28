package network.movie

import core.entity.Movie
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements
import usecase.movie.GetMovieInterface
import utils.DateUtils

class GetMovieByCrawl extends GetMovieInterface {

  // Link source
  private val CURRENT_MOVIE_URL: String = "http://lichphim.vn/fbapp/lichchieu"
  private val NEW_MOVIE_URL: String = "http://lichphim.vn/fbapp/sapchieu"
  private val CINEMA_URL: String = "http://lichphim.vn/fbapp/rapchieu"

  private var document: Document = _
  private var url: String = _

  // data list
//  private var movieList: List[Movie] = _

  def setUrlSource(url: String)= {
    this.url = url
    document = Jsoup.connect(url).userAgent("Mozilla").get()
  }

  def  getMovieList(isCurrent: Boolean): List[Movie] = {
    val movieList: List[Movie] = List()
    if (isCurrent) {
      setUrlSource(CURRENT_MOVIE_URL)
    } else {
      setUrlSource(NEW_MOVIE_URL)
    }

    val elementItems: Elements = document.select("div[class=img_item_phim]")
    print(elementItems)

//    for (e:Element <- elementItems) {
//      val items: Elements = e.select("a")
//      for (e1: Element <- items) {
//        val detailLink: String = e1.attr("href")
//        val titleContent: String = e1.attr("title")
//        val imgSource: Elements = e1.select("img")
//        val img: String = imgSource.attr("src")
//
//        solveTitleContent(img, detailLink, titleContent, isCurrent) :: movieList
//      }
//    }
    movieList
  }

  private def solveTitleContent(img: String, detailLink: String, titleContent: String, isCurrent: Boolean): Movie = {

    // Create document from string
    val doc: Document = Jsoup.parse(titleContent)

    // Filter span tag
    val elements: Elements = doc.select("span")

    if (isCurrent) {
        val title = elements.get(0).text
        val _type = elements.get(2).text
        val time = elements.get(4).text
        val director = elements.get(6).text
        val actor = elements.get(8).text

        val imdbPoint = elements.get(10).text
        val dayStart = DateUtils.convertDay(elements.get(12).text)
        val content = elements.get(13).text

        Movie(title, _type, img, detailLink, time, director, actor, imdbPoint, dayStart, content)
    } else {
        val title = elements.get(0).text
        val _type = elements.get(2).text
        val time = elements.get(4).text
        val director = elements.get(6).text
        val actor = elements.get(8).text

        val dayStart = DateUtils.convertDay(elements.get(10).text)
        val content = elements.get(11).text

        Movie(title, _type, img, detailLink, time, director, actor, "0", dayStart, content)
    }
  }

  override def getAll(isCurrent: Boolean): List[Movie] = getMovieList(isCurrent)
}