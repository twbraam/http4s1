package http4s.d_authentication.authentication

import cats.implicits._
import org.http4s.dsl.io.{Forbidden, Ok}
import org.reactormonk.{CryptoBits, PrivateKey}

import cats.data._
import cats.effect._
import http4s.d_authentication.User
import org.http4s._
import org.http4s.dsl.io._

object CookiesAuth {

  val key = PrivateKey(scala.io.Codec.toUTF8(scala.util.Random.alphanumeric.take(20).mkString("")))
  val crypto = CryptoBits(key)
  val clock = java.time.Clock.systemUTC


  def retrieveUser: Kleisli[IO, Long, User] = Kleisli(id => IO(User(id, "Unknowwwwn")))
  val authUser: Kleisli[IO, Request[IO], Either[String,User]] = Kleisli({ request =>
    val message = for {
      header <- headers.Cookie.from(request.headers).toRight("Cookie parsing error")
      cookie <- header.values.toList.find(_.name == "authcookie").toRight("Couldn't find the authcookie")
      token <- crypto.validateSignedToken(cookie.content).toRight("Cookie invalid")
      message <- Either.catchOnly[NumberFormatException](token.toLong).leftMap(_.toString)
    } yield message
    message.traverse(retrieveUser.run)
  })

  def verifyLogin(request: Request[IO]): IO[Either[String,User]] = {
    println(request.attributes)
    IO(Right(User(12, "Bert")))
  } // gotta figure out how to do the form

  val logIn: Kleisli[IO, Request[IO], Response[IO]] = Kleisli({ request =>
    verifyLogin(request).flatMap {
      case Left(error) =>
        Forbidden(error)
      case Right(user) =>
        val message = crypto.signToken(user.id.toString, clock.millis.toString)
        Ok("Logged in!").map(_.addCookie(ResponseCookie("authcookie", message)))
    }
  })


}
