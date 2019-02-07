import org.scalatest.{FunSuite, Matchers}

/**
  * Created by mtumilowicz on 2019-02-05.
  */
class NaturalTransformationsTest extends FunSuite with Matchers {

  test("testSafeHead") {
    ListOptionNaturalTransformation.safeHead(List(1)) should be(Some(1))
    ListOptionNaturalTransformation.safeHead(List()) should be(None)
  }

  test("trivial") {
    def reader: Reader[Unit, String] = _ => "a"

    ReaderOptionNaturalTransformation.trivial(reader) should be(None)
    ReaderOptionNaturalTransformation.obvious(reader) should be(Some("a"))
  }

}
