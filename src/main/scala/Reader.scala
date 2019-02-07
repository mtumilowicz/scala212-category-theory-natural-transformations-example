/**
  * Created by mtumilowicz on 2019-02-05.
  */
trait Reader[R, A] extends (R => A) {
  
  def map[B](f: A => B): Reader[R, B] = asReader(f compose this)

  def asReader[X, Y](f: X => Y): Reader[X, Y] = f.apply
}