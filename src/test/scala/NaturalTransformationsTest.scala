import functor.{Const, Reader}
import org.scalatest.{FunSuite, Matchers}
import transformation._

/**
  * Created by mtumilowicz on 2019-02-05.
  */
class NaturalTransformationsTest extends FunSuite with Matchers {

  test("list -> option: safe head") {
    ListToOption.safeHead(List(1)) should be(Some(1))
    ListToOption.safeHead(List()) should be(None)
  }

  test("option -> list") {
    OptionToList.toList(None) should be(List())
    OptionToList.toList(Some(1)) should be(List(1))
  }

  test("reader () a -> option a: trivial, obvious") {
    def reader: Reader[Unit, String] = _ => "a"

    ReaderUnitToOption.trivial(reader) should be(None)
    ReaderUnitToOption.obvious(reader) should be(Some("a"))
  }
  test("reader () a -> list a: trivial, obvious") {
    def reader: Reader[Unit, String] = _ => "a"

    ReaderUnitToList.trivial(reader) should be(List())
    ReaderUnitToList.obvious(reader) should be(List("a"))
  }

  test("[a] -> const int a: length") {
    ListToInt.lengthOf(List()) should be(Const(0))
    ListToInt.lengthOf(List(1, 2, 3, 4)) should be(Const(4))
  }

  test("reader boolean a -> option a: trivial, truth, untruth") {
    def reader: Reader[Boolean, String] = {
      case true => "truth"
      case false => "untruth"
    }
    
    ReaderBooleanToOption.trivial(reader) should be (None)
    ReaderBooleanToOption.truth(reader) should be (Some("truth"))
    ReaderBooleanToOption.untruth(reader) should be (Some("untruth"))
  }
}
