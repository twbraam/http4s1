package http4s.d_authentication.services

import cats.data._
import cats.effect._
import cats.implicits._
import http4s.d_authentication.User
import http4s.d_authentication.authentication.{CookiesAuth, HeaderAuth}
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.server._

object Simple {

  // val authUser: Kleisli[OptionT[IO, *], Request[IO], User] = Kleisli(x => OptionT.liftF(IO(User(123, "bert"))))
  // val middleware: AuthMiddleware[IO, User] = AuthMiddleware(authUser)

  // ^ without error handling

  val onFailure: AuthedRoutes[String, IO] = Kleisli(req => OptionT.liftF(Forbidden(req.context)))
  val cookieMiddleware = AuthMiddleware(CookiesAuth.authUser, onFailure)
  val headerMiddleware = AuthMiddleware(HeaderAuth.authUser, onFailure)

  val authedRoutes: AuthedRoutes[User, IO] =
    AuthedRoutes.of {
      case GET -> Root / "welcome" as user => Ok(s"Welcome, ${user.name}")
    }

  val loginRoutes: HttpRoutes[IO] =
    HttpRoutes.of {
      case GET -> Root / "bonjour" => Ok("Bonjour")
    }

  val service: HttpRoutes[IO] = loginRoutes <+> headerMiddleware(authedRoutes)


}
