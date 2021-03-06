[![Build Status](https://travis-ci.com/mtumilowicz/scala212-category-theory-natural-transformations-example.svg?token=PwyvjePQ7aiAX51hSYLE&branch=master)](https://travis-ci.com/mtumilowicz/scala212-category-theory-natural-transformations-example)

# scala212-category-theory-natural-transformations-example

https://bartoszmilewski.com/2015/04/07/natural-transformations/

# preview
* consider two functors `F` and `G` between categories `C` and `D`
* a natural transformation is a selection of morphisms: 
    for every object `a`, it picks one morphism from `F a` to `G a`. 
    If we call the natural transformation `α,` this morphism is 
    called the component of `α` at `a`, or `αa`.
* `αa :: F a -> G a`
* `a` is an object in `C` while `αa` is a morphism in `D`
* if, for some `a`, there is no morphism between `F a` and `G a` in `D`, 
    there can be no natural transformation between `F` and `G`
* naturality condition: `G f ∘ αa = αb ∘ F f`
* we can think about functors as generalized containers 
* we can consider natural transformations to be recipes for repackaging the contents of one container into 
    another and **we are not touching the items themselves**
* naturality condition: it doesn’t matter whether we modify the items first (fmap), 
and repackage later; or repackage first, and then modify the items in the new container (with new fmap)
* existence of natural transformations between two functors is far from guaranteed
* it maps objects to morphisms
* it maps morphisms to commuting squares — there is one commuting naturality square in `D` 
    for every morphism in `C` (naturality condition)


# polymorphic function
* `αa :: F a -> G a`
* pseudo-haskell: `alpha_a :: F a -> G a`
* pseudo-scala: `def alpha_a[A](F: F[A]): G[A]`
* naturality condition: `mapG f . alpha_a = alpha_b . mapF f`
* a parametrically polymorphic function between two functors 
    is always a natural transformation

# project description
We will provide easy examples of polymorphic functions (natural transformations) in scala
* list -> option (some(head) or none)
    * haskell
        ```
        safeHead :: [a] -> Maybe a
        safeHead [] = Nothing
        safeHead (x:xs) = Just x
        ```
    * scala
        ```
        def safeHead[A](list: List[A]): Option[A] = list match {
          case Nil => None
          case x :: xs => Some(x)
        }
        ```
        **note that list has build-in exactly that method:** `list.headOption`
* option -> list (one elem or empty)
    ```
    def toList[A](option: Option[A]): List[A] = option match {
      case None => Nil
      case Some(x) => List(x)
    }
    ```
    **note that option has build-in exactly that method:** `option.toList`
* list a -> int (const int a): length
    * Const: https://github.com/mtumilowicz/scala212-category-theory-const-functor
    * haskell
        ```
        length :: [a] -> Const Int a
        length [] = Const 0
        length (x:xs) = Const (1 + unConst (length xs))
        
        unConst :: Const c a -> c
        unConst (Const x) = x
        ```
    * scala
        ```
        def lengthOf[A](list: List[A]): Const[Int, A] = list match {
          case Nil => Const(0)
          case x :: xs => Const(1 + lengthOf(xs).param)
        }
        ```
        **obviously:** `list.length` is much easier
* reader () a -> option a
    * for every type e, you can define a family of natural transformations from `Reader e` to any other functor `f`
    * `Reader ()` takes any type `a` and maps it into a function type `()->a` - all the functions that 
        pick a single element from the set `a`
    * we could think about `Reader ()` as a supplier
    * haskell
        * `alpha :: Reader () a -> Maybe a`
        * only two possible implementations:
            * `dumb (Reader _) = Nothing`
            * `obvious (Reader g) = Just (g ())`
    * scala
        ```
        object ReaderOptionNaturalTransformation {
          def trivial[A](reader: Reader[Unit, A]): Option[A] = None
          def obvious[A](reader: Reader[Unit, A]): Option[A] = Some(reader())
        }
        ```
* reader () a -> list a: trivial, obvious (composition of defined above natural transformations)
    ```
    def trivial[A](reader: Reader[Unit, A]): List[A] = {
      val toList = (option: Option[A]) => OptionListNaturalTransformation.toList(option)
      val trivial = (reader: Reader[Unit, A]) => ReaderOptionNaturalTransformation.trivial(reader)
    
      toList.compose(trivial).apply(reader)
    }
    
    def obvious[A](reader: Reader[Unit, A]): List[A] = {
      val toList = (option: Option[A]) => OptionListNaturalTransformation.toList(option)
      val trivial = (reader: Reader[Unit, A]) => ReaderOptionNaturalTransformation.obvious(reader)
    
      toList.compose(trivial).apply(reader)
    }
    ```
* reader boolean a -> option a: three transformations
    ```
    def trivial[A](reader: Reader[Boolean, A]): Option[A] = None
    def truth[A](reader: Reader[Boolean, A]): Option[A] = Some(reader(true))
    def untruth[A](reader: Reader[Boolean, A]): Option[A] = Some(reader(false))
    ```
# tests
* `NaturalTransformationsTest`
* list -> option: safe head
    ```
    ListOptionNaturalTransformation.safeHead(List(1)) should be(Some(1))
    ListOptionNaturalTransformation.safeHead(List()) should be(None)
    ```
* option -> list
    ```
    OptionListNaturalTransformation.toList(None) should be (List())
    OptionListNaturalTransformation.toList(Some(1)) should be (List(1))
    ```
* reader () a -> option a: trivial, obvious
    ```
    def reader: Reader[Unit, String] = _ => "a"
    
    ReaderOptionNaturalTransformation.trivial(reader) should be(None)
    ReaderOptionNaturalTransformation.obvious(reader) should be(Some("a"))
    ```
* reader () a -> list a: trivial, obvious (composition of defined above natural transformations)
    ```
    def trivial[A](reader: Reader[Unit, A]): List[A] =
      OptionListNaturalTransformation.toList[A].compose(ReaderOptionNaturalTransformation.trivial[A]).apply(reader)
    
    def obvious[A](reader: Reader[Unit, A]): List[A] =
      OptionListNaturalTransformation.toList[A].compose(ReaderOptionNaturalTransformation.obvious[A]).apply(reader)
    ```
* reader boolean a -> option a: three transformations
    ```
    ReaderBooleanToOption.trivial(reader) should be (None)
    ReaderBooleanToOption.truth(reader) should be (Some("true"))
    ReaderBooleanToOption.untruth(reader) should be (Some("false"))
    ```
* list a -> const int a: length
    ```
    ListLengthAsNaturalTransformation.lengthOf(List()) should be(Const(0))
    ListLengthAsNaturalTransformation.lengthOf(List(1, 2, 3, 4)) should be(Const(4))
    ```