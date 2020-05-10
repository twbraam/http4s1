package http4s.b_DSL

import cats.effect._
import org.http4s.implicits._
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext.Implicits.global


object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(services.WeatherOptional.routes.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)

}
