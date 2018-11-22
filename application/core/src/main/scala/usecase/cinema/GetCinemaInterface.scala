package usecase.cinema

import core.entity.Cinema

trait GetCinemaInterface {
  def getAll: List[Cinema]
}
