package http4s.f_csrf.services

import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.middleware._


object Simple {
  val service = HttpRoutes.of[IO] {
    case _ =>
      Ok()
  }

  val cookieName = "csrf-token"
  val key  = CSRF.generateSigningKey[IO].unsafeRunSync
  val defaultOriginCheck: Request[IO] => Boolean =
    CSRF.defaultOriginCheck[IO](_, "localhost", Uri.Scheme.http, None)
  val csrfBuilder = CSRF[IO,IO](key, defaultOriginCheck)

  val csrf: CSRF[IO, IO] = csrfBuilder.withCookieName(cookieName).withCookieDomain(Some("localhost")).withCookiePath(Some("/")).build

  val csrfService = csrf.validate()(service.orNotFound)

}
