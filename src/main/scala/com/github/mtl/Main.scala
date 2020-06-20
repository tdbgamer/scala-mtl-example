package com.bcf.mtl

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
import cats.Monad
import java.{util => ju}
import com.github.mtl.app.MockApp
import com.github.mtl.algebra.User
import com.github.mtl.algebra.UserAlg
import com.github.mtl.algebra.ConsoleAlg

object Main extends IOApp {
  def print[F[_]: Sync, A: Show](showable: A): F[Unit] =
    Sync[F].delay(println(showable.show))

  /**
    * Program definition purely based on
    * Monad + Algebras
    * 
    * Testing implementations of the algebras can be used
    * to "mock" effects and ensure correctness.
    * 
    * Prod implementation actually uses IO monad to do
    * the actual effects in production.
    */
  def program[F[_]: UserAlg: ConsoleAlg: Monad](): F[Unit] =
    for {
      createdUser <- UserAlg[F].createUser("Tim", "timbessmail@gmail.com")
      user <- UserAlg[F].getUser(createdUser.id)
      _ <- ConsoleAlg[F].print(user)
    } yield ()

  override def run(args: List[String]): IO[ExitCode] = {
    MockApp
      .runMockApp(program[MockApp])
      .as(ExitCode.Success)
  }
}
