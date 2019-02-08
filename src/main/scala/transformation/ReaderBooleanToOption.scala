package transformation

import functor.Reader

/**
  * Created by mtumilowicz on 2019-02-08.
  */
object ReaderBooleanToOption {
  def trivial[A](reader: Reader[Boolean, A]): Option[A] = None
  def truth[A](reader: Reader[Boolean, A]): Option[A] = Some(reader(true))
  def untruth[A](reader: Reader[Boolean, A]): Option[A] = Some(reader(false))
}
