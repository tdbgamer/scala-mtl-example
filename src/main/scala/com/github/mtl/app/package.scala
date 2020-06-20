package com.github.mtl

import cats.implicits._
import cats.data._
import cats.effect.IOApp
import cats.effect.{ExitCode, IO}
import cats.effect.Sync
import cats.Show
import cats.mtl._
import cats.mtl.instances.all._
import io.estatico.newtype.macros.newtype
import cats.effect.ExitCase
import com.github.mtl.algebra.UserAlg
import com.github.mtl.algebra.User
import cats.Monad
import java.util.UUID
import com.github.mtl.algebra.ConsoleAlg

package object app {
  /**
    * Mock implementation of all algebras required to run.
    * 
    * Uses MonadState to simulate the database via a Map
    * Uses Sync for ID generation and printing to the console
    */
  @newtype case class MockApp[A](
      runMockApp: StateT[IO, Map[String, User], A]
  )

  object MockApp {
    private type MonadStateEnv[F[_]] = MonadState[F, Map[String, User]]

    implicit val sync: Sync[MockApp] = derivingK
    implicit val state: MonadStateEnv[MockApp] = derivingK

    implicit val userAlg: UserAlg[MockApp] = new UserAlg[MockApp] {
      private val state = implicitly[MonadStateEnv[MockApp]]
      override def getUser(id: String): MockApp[Option[User]] =
        for {
          users <- state.get
        } yield users.get(id)
      override def createUser(name: String, email: String): MockApp[User] =
        for {
          users <- state.get
          randomId <- Sync[MockApp].delay(UUID.randomUUID().toString)
          user = User(randomId, name, email)
          _ <- state.set(users.updated(randomId, user))
        } yield user
    }

    implicit val consoleAlg: ConsoleAlg[MockApp] = new ConsoleAlg[MockApp] {
      override def print[A: Show](showable: A): MockApp[Unit] =
        Sync[MockApp].delay(println(showable.show))
    }

    /**
      * Convenience function to execute the app with empty initial state
      * and return IO result.
      */
    def runMockApp[A](program: MockApp[A]): IO[A] =
      program.runMockApp
        .run(Map.empty)
        .map(_._2)
  }
}
