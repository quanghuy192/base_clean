package repository

import core.entity.cinema.Cinema

import scala.util.Try

trait CinemaRepository {
  def getAll: List[Cinema]
  def store(cinema: Cinema): Try[Int]
}
