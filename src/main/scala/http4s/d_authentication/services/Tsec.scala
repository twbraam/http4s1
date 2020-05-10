package http4s.d_authentication.services

import cats.data._
import cats.effect._
import cats.implicits._
import http4s.d_authentication.User
import http4s.d_authentication.authentication.{CookiesAuth, HeaderAuth, TsecAuth}
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.server._
import cats._
import cats.data.OptionT
import cats.effect.{IO, Sync}
import cats.implicits._
import org.http4s.HttpService
import org.http4s.dsl.io._
import tsec.authentication._
import tsec.authorization._
import tsec.cipher.symmetric.jca._
import tsec.common.SecureRandomId
import tsec.jws.mac.JWTMac

import scala.collection.mutable
import scala.concurrent.duration._

object Tsec {

  /*
  Now from here, if want want to create services, we simply use the following
  (Note: Since the type of the service is HttpService[IO], we can mount it like any other endpoint!):
   */
  val service: HttpRoutes[IO] = TsecAuth.Auth.liftService(TSecAuthService {
    //Where user is the case class User above
    case request@GET -> Root / "api" asAuthed user =>
      /*
      Note: The request is of type: SecuredRequest, which carries:
      1. The request
      2. The Authenticator (i.e token)
      3. The identity (i.e in this case, User)
       */
      val r: SecuredRequest[IO, User, TSecBearerToken[Int]] = request
      Ok()
  })
}
