package transformation

/**
  * Created by mtumilowicz on 2019-02-07.
  */
object ReaderUnitToOption {
  def trivial[A](reader: Reader[Unit, A]): Option[A] = None
  def obvious[A](reader: Reader[Unit, A]): Option[A] = Some(reader())
}
