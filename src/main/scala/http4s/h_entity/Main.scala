package http4s.h_entity

import cats.effect.{IO, _}
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext.global

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(services.StreamService.routes.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)

}
