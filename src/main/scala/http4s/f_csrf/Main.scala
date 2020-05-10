package http4s.f_csrf

import cats.effect._
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext.Implicits.global


object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(services.Simple.csrfService)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}

// curl -v http://localhost:8080/ -H "Origin: http://localhost"
// curl -X POST -v http://localhost:8080/ -H "Origin: http://localhost" -H "X-Csrf-Token: {token}" --cookie "csrf-token={token}"
