package http4s.d_authentication.authentication


import cats.implicits._
import org.http4s.dsl.io.{Forbidden, Ok}
import org.reactormonk.{CryptoBits, PrivateKey}
import org.http4s.util.string._
import org.http4s.headers.Authorization

import cats.data._
import cats.effect._
import http4s.d_authentication.User
import org.http4s._
import org.http4s.dsl.io._

object HeaderAuth {
  def retrieveUser: Kleisli[IO, Long, User] = Kleisli(id => IO(User(id, "Unknowwwwn")))

  val key = PrivateKey(scala.io.Codec.toUTF8(scala.util.Random.alphanumeric.take(20).mkString("")))
  val crypto = CryptoBits(key)
  val clock = java.time.Clock.systemUTC

  val authUser: Kleisli[IO, Request[IO], Either[String,User]] = Kleisli({ request =>
    val message = for {
      header <- request.headers.get(Authorization).toRight("Couldn't find an Authorization header")
      token <- crypto.validateSignedToken(header.value).toRight("Invalid token")
      message <- Either.catchOnly[NumberFormatException](token.toLong).leftMap(_.toString)
    } yield message
    message.traverse(retrieveUser.run)
  })

}
