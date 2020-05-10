package http4s.d_authentication.authentication

import cats.effect.IO
import http4s.d_authentication.User
import http4s.d_authentication.authentication.tsecauth.TsecHelpers._
import tsec.authentication._
import tsec.common.SecureRandomId

import scala.concurrent.duration._




object TsecAuth {



  val bearerTokenStore =
    dummyBackingStore[IO, SecureRandomId, TSecBearerToken[Int]](s => SecureRandomId.coerce(s.id))



  //We create a way to store our users. You can attach this to say, your doobie accessor
  val userStore: BackingStore[IO, Int, User] = dummyBackingStore[IO, Int, User](_.id.toInt)

  val settings: TSecTokenSettings = TSecTokenSettings(
    expiryDuration = 10.minutes, //Absolute expiration time
    maxIdle = None
  )

  val bearerTokenAuth =
    BearerTokenAuthenticator(
      bearerTokenStore,
      userStore,
      settings
    )

  val Auth = SecuredRequestHandler(bearerTokenAuth)

}
