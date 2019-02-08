package transformation

/**
  * Created by mtumilowicz on 2019-02-05.
  */
object ListToOption {
  def safeHead[A](list: List[A]): Option[A] = list match {
    case Nil => None
    case x :: xs => Some(x)
  }
}
