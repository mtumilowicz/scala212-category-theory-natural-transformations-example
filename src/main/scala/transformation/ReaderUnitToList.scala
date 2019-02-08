package transformation

/**
  * Created by mtumilowicz on 2019-02-08.
  */
object ReaderUnitToList {
  def trivial[A](reader: Reader[Unit, A]): List[A] = {
    val toList = (option: Option[A]) => OptionToList.toList(option)
    val trivial = (reader: Reader[Unit, A]) => ReaderUnitToOption.trivial(reader)

    toList.compose(trivial).apply(reader)
  }

  def obvious[A](reader: Reader[Unit, A]): List[A] = {
    val toList = (option: Option[A]) => OptionToList.toList(option)
    val trivial = (reader: Reader[Unit, A]) => ReaderUnitToOption.obvious(reader)

    toList.compose(trivial).apply(reader)
  }
}
