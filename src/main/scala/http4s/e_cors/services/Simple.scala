package http4s.e_cors.services

import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.server.middleware._

import scala.concurrent.duration._


object Simple {

  val service = HttpRoutes.of[IO] {
    case _ =>
      Ok()
  }

  val corsService = CORS(service) // curl -v http://localhost:8080/ -H "Origin: https://somewhere.com"

  /////////////////////////////////////

  val onlyAllowGetPost = CORSConfig(
    anyOrigin = true,
    anyMethod = false,
    allowedMethods = Some(Set("GET", "POST")),
    allowCredentials = true,
    maxAge = 1.day.toSeconds)

  val corsServiceConfigured1 = CORS(service, onlyAllowGetPost)

  /////////////////////////////////////

  val onlyAllowCertainWebsite = CORSConfig(
    anyOrigin = false,
    allowedOrigins = Set("https://yahoo.com", "https://duckduckgo.com"),
    allowCredentials = false,
    maxAge = 1.day.toSeconds)

  val corsServiceConfigured2 = CORS(service, onlyAllowCertainWebsite)
}
