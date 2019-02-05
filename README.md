# scala212-category-theory-natural-transformations-example

https://bartoszmilewski.com/2015/04/07/natural-transformations/

safeHead :: [a] -> Maybe a
safeHead [] = Nothing
safeHead (x:xs) = Just x

length :: [a] -> Const Int a
length [] = Const 0
length (x:xs) = Const (1 + unConst (length xs))

unConst :: Const c a -> c
unConst (Const x) = x

alpha :: Reader () a -> Maybe a
dumb (Reader _) = Nothing
obvious (Reader g) = Just (g ())