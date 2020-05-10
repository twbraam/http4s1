package http4s.d_authentication.authentication

import cats.implicits._
import org.http4s.dsl.io.{Forbidden, Ok}
import org.reactormonk.{CryptoBits, PrivateKey}

import cats.data._
import cats.effect._
import http4s.d_authentication.User
import org.http4s._
import org.http4s.dsl.io._
import tsecauth.TsecHelpers._ // import dummyBackingStore factory

import java.util.UUID


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



import scala.collection.mutable
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
