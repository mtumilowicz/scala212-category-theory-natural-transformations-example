# scala212-category-theory-natural-transformations-example

https://bartoszmilewski.com/2015/04/07/natural-transformations/

# preview
* consider two functors `F` and `G` between categories `C` and `D`
* a natural transformation is a selection of morphisms: 
    for every object a, it picks one morphism from F a to G a. 
    If we call the natural transformation α, this morphism is 
    called the component of α at a, or αa.
* αa :: F a -> G a
* a is an object in C while αa is a morphism in D
* If, for some a, there is no morphism between F a and G a in D, there can be no natural transformation between F and G.
* naturality condition: G f ∘ αa = αb ∘ F f
* existence of natural transformations between two functors is far from guaranteed
* it maps objects to morphisms
* it maps morphisms to commuting squares — there is one commuting naturality square in D 
for every morphism in C (naturality condition)

* naturality condition becomes the statement that it doesn’t matter whether we modify the items first, through the application of fmap, and repackage later; or repackage first, and then modify the items in the new container, with its own implementation of fmap

* One way of thinking about functors in Haskell that I mentioned earlier is to consider them generalized containers. We can continue this analogy and consider natural transformations to be recipes for repackaging the contents of one container into another container. We are not touching the items themselves

# project description
* safeHead
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
* length
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
* Reader -> Option
    * For every type e, you can define a family of natural transformations from `Reader e` to any other functor `f`
    * `Reader ()` takes any type `a` and maps it into a function type `()->a`. 
        These are just all the functions that pick a single element from the set `a`.
    * haskell
        * `alpha :: Reader () a -> Maybe a`
        * `dumb (Reader _) = Nothing`
        * `obvious (Reader g) = Just (g ())`
    * scala
        ```
        object ReaderOptionNaturalTransformation {
          def trivial[A](reader: Reader[Unit, A]): Option[A] = None
          def obvious[A](reader: Reader[Unit, A]): Option[A] = Some(reader())
        }
        ```

# polymorphic function
* αa :: F a -> G a
* pseudo-haskell: alpha_a :: F a -> G a
* pseudo-scala: def alpha_a[A](F: F[A]): G[A]
* naturality condition: `mapG f . alpha_a = alpha_b . mapF f`


# definition

αa :: F a -> G a
Keep in mind that a is an object in C while αa is a morphism in D.

If, for some a, there is no morphism between F a and G a in D, there can be no natural transformation between F and G.


alpha :: Reader () a -> Maybe a
dumb (Reader _) = Nothing
obvious (Reader g) = Just (g ())