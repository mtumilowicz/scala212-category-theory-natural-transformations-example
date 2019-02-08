package transformation

/**
  * Created by mtumilowicz on 2019-02-08.
  */
object OptionToList {
  def toList[A](option: Option[A]): List[A] = option match {
    case None => Nil
    case Some(x) => List(x)
  }
}
