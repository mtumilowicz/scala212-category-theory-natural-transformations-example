/**
  * Created by mtumilowicz on 2019-02-07.
  */
object ReaderUnitOptionNaturalTransformation {
  def trivial[A](reader: Reader[Unit, A]): Option[A] = None
  def obvious[A](reader: Reader[Unit, A]): Option[A] = Some(reader())
}
