import org.scalatest.{FunSuite, Matchers}

/**
  * Created by mtumilowicz on 2019-02-05.
  */
class NaturalTransformationsTest extends FunSuite with Matchers {

  test("list -> option: safe head") {
    ListOptionNaturalTransformation.safeHead(List(1)) should be(Some(1))
    ListOptionNaturalTransformation.safeHead(List()) should be(None)
  }
  
  test("option -> list") {
    OptionListNaturalTransformation.toList(None) should be (List())
    OptionListNaturalTransformation.toList(Some(1)) should be (List(1))
  }

  test("reader -> option: trivial, obvious") {
    def reader: Reader[Unit, String] = _ => "a"

    ReaderOptionNaturalTransformation.trivial(reader) should be(None)
    ReaderOptionNaturalTransformation.obvious(reader) should be(Some("a"))
  }
  test("reader -> list: trivial, obvious") {
    def reader: Reader[Unit, String] = _ => "a"

    ReaderListNaturalTransformation.trivial(reader) should be(List())
    ReaderListNaturalTransformation.obvious(reader) should be(List("a"))
  }

  test("[a] -> const int a: length") {
    ListLengthAsNaturalTransformation.lengthOf(List()) should be(Const(0))
    ListLengthAsNaturalTransformation.lengthOf(List(1, 2, 3, 4)) should be(Const(4))
  }
}
