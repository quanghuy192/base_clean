package repository

import core.entity.movie.Movie

import scala.util.Try

trait MovieRepository {
  def getAll: List[Movie]
  def store(cinema: Movie): Try[Int]
  def storeAll(cinema: List[Movie]): Try[Int]
}
