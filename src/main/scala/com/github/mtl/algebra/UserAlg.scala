package com.github.mtl.algebra

import simulacrum.typeclass
import cats.effect.Sync
import cats.Show

final case class User(id: String, name: String, email: String)
object User {
  implicit val show: Show[User] = Show.fromToString
}

@typeclass trait UserAlg[F[_]] {
  def getUser(id: String): F[Option[User]]
  def createUser(name: String, email: String): F[User]
}