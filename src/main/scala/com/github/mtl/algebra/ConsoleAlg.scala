package com.github.mtl.algebra

import cats.Show
import simulacrum.typeclass

@typeclass trait ConsoleAlg[F[_]] {
  def print[A: Show](showable: A): F[Unit]
}