package http4s.b_DSL.services

import cats.effect._
import org.http4s._
import org.http4s.dsl.io._

import scala.concurrent.ExecutionContext.Implicits.global

object Cookies {
  implicit val timer: Timer[IO] = IO.timer(global)
  val cookieResp = {
    for {
      resp <- Ok("Ok response.")
      now <- HttpDate.current[IO]
    } yield resp.addCookie(ResponseCookie("foo", "bar", expires = Some(now), httpOnly = true, secure = true))
  }

  val cookieService = HttpRoutes.of[IO] {
    case _ => cookieResp
  }
}
