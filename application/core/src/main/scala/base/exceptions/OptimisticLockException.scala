package base.exceptions

/**
 * Represents optimistic locking conflict.
 * @param message message
 * @param cause cause
 */
class OptimisticLockException(message: String, cause: Option[Throwable] = None)
  extends BaseException(message, cause) {

  def this(message: String, cause: Throwable) = this(message, Some(cause))
}

