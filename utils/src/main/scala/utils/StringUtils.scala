package utils

object StringUtils {
  def isEmpty(s: String): Boolean = s == null || s.length <= 0
}
