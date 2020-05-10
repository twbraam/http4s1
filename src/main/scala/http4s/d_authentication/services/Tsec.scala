package http4s.d_authentication.services

import cats.effect.IO
import http4s.d_authentication.User
import http4s.d_authentication.authentication.TsecAuth
import org.http4s._
import org.http4s.dsl.io._
import tsec.authentication._

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
