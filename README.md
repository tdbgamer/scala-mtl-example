# Finally Tagless MTL Example

This is written in Haskell style using newtype deriving via `estatico/scala-newtype`.

Certain things aren't quite as nice as with Haskell, like place countext bounds on monad accepted by the algebras.
This makes it a bit verbose having to put `Monad` constraints in program definition, but isn't terrible.
