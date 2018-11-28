package utils

object DateUtils {

  // Convert day type yyyy-mm-dd to dd/mm/yyyy
  def  convertDay(day: String): String = {
    val builder = new StringBuilder
    val partOfString = day.split("-")
    for (i <- partOfString.length - 1 to 0 by -1) {
      builder.append(partOfString(i))
      builder.append("/")
    }
    val dayAfterConvert = builder.toString
    dayAfterConvert.substring(0, dayAfterConvert.length - 1)
  }
}
