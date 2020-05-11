package http4s.h_entity.services

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import cats.effect._
import fs2.Stream
import org.http4s._
import org.http4s.dsl.io._

object StreamService {

  implicit val timer: Timer[IO] = IO.timer(global)
  implicit val cs: ContextShift[IO] = IO.contextShift(global)

  // An infinite stream of the periodic elapsed time
  val seconds = Stream.awakeEvery[IO](1.second)

  val routes = HttpRoutes.of[IO] {
    case GET -> Root / "seconds" =>
      Ok(seconds.map(_.toString)) // `map` `toString` because there's no `EntityEncoder` for `Duration`
  }

}
