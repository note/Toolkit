package utils

/**
  * Contains functions for IO handling
  *
  * @author Lukas Zimmermann
  */
object IO {

  def autoClose[A <: AutoCloseable,B](closeable: A)(fun: A => B): B = {

    var t: Throwable = null
    try {
      fun(closeable)
    } catch {
      case funT: Throwable ⇒
        t = funT
        throw t
    } finally {
      if (t != null) {
        try {
          closeable.close()
        } catch {
          case closeT: Throwable ⇒
            t.addSuppressed(closeT)
            throw t
        }
      } else {
        closeable.close()
      }
    }
  }
}
