/**
  * Created by mtumilowicz on 2019-02-07.
  */
object ListLengthAsNaturalTransformation {
  def lengthOf[A](list: List[A]): Const[Int, A] = list match {
    case Nil => Const(0)
    case x :: xs => Const(1 + lengthOf(xs).param)
  }
}
