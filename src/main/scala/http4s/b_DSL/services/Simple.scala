package http4s.b_DSL.services

import cats.data.NonEmptyList
import cats.effect._
import org.http4s.CacheDirective.`no-cache`
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.headers.`Cache-Control`

object Simple {
  val service = HttpRoutes.of[IO] {
    case GET -> "hello" /: rest => Ok(s"""Hello, ${rest.toList.mkString(" and ")}!""")
    case GET -> Root / "file" / file ~ "json" => Ok(s"""You asked for $file""")
    case GET -> Root / "users" / IntVar(userId) => Ok(s"userId is: $userId")
    case _ => Ok("Ok response.", `Cache-Control`(NonEmptyList(`no-cache`(), Nil)), Header("X-Auth-Token", "value"))
      .map(_.addCookie(ResponseCookie("foo", "bar")))
  }
}