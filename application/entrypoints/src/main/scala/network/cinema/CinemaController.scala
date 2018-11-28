package network.cinema

import javax.inject._
import play.api.mvc._
import usecase.movie.GetMovieInterface

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class CinemaController @Inject()(cc: ControllerComponents, movieRepository: GetMovieInterface) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def list = Action {
    val movieList = movieRepository.getAll(true)
    Ok
  }

}
