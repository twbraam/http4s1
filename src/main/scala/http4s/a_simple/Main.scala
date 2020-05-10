package http4s.a_simple

import cats.effect._
import cats.implicits._
import http4s.a_simple.Service.{helloWorldService, tweetService}
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext.Implicits.global



object Main extends IOApp {

  val services = tweetService <+> helloWorldService

  val httpApp = Router("/" -> helloWorldService, "/api" -> services).orNotFound

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(httpApp)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)


}
