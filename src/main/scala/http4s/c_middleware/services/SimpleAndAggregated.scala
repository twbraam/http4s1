package http4s.c_middleware.services

import cats.effect._
import cats.implicits._
import http4s.c_middleware.middleware.AddHeader
import org.http4s._
import org.http4s.dsl.io._

object SimpleAndAggregated {

  val service = HttpRoutes.of[IO] {
    case GET -> Root / "bad" => BadRequest()
    case _ => Ok()
  }
  val wrappedService = AddHeader(service, Header("SomeKey", "SomeValue"))

  ///////////////////////////////////////////////////////////////

  val apiService = HttpRoutes.of[IO] {
    case GET -> Root / "api" =>
      Ok()
  }

  val aggregateService = apiService <+> wrappedService

}
