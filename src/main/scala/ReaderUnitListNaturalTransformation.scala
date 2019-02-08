/**
  * Created by mtumilowicz on 2019-02-08.
  */
object ReaderUnitListNaturalTransformation {
  def trivial[A](reader: Reader[Unit, A]): List[A] = {
    val toList = (option: Option[A]) => OptionListNaturalTransformation.toList(option)
    val trivial = (reader: Reader[Unit, A]) => ReaderUnitOptionNaturalTransformation.trivial(reader)

    toList.compose(trivial).apply(reader)
  }

  def obvious[A](reader: Reader[Unit, A]): List[A] = {
    val toList = (option: Option[A]) => OptionListNaturalTransformation.toList(option)
    val trivial = (reader: Reader[Unit, A]) => ReaderUnitOptionNaturalTransformation.obvious(reader)

    toList.compose(trivial).apply(reader)
  }
}
