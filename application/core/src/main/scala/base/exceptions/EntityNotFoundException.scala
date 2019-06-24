package base.exceptions

class EntityNotFoundException(message: String, cause: Option[Throwable] = None)
  extends BaseException(message, cause) {

  def this(message: String, cause: Throwable) = this(message, Some(cause))
}
