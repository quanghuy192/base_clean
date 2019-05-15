package network.movie.db

import core.entity.movie.Movie
import repository.MovieRepository

import scala.util.Try

class MovieRepositoryOnMySQLImpl extends MovieRepository{

  type DAO <: CRUDMapperWithId
  protected[this] val dao: DAO

  override def getAll: List[Movie] = ???

  override def store(cinema: Movie): Try[Int] = ???

  override def storeAll(cinema: List[Movie]): Try[Int] = ???
}
