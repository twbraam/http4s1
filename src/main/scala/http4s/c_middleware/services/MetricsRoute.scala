package http4s.c_middleware.services

import cats.effect._
import com.codahale.metrics.SharedMetricRegistries
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.metrics.dropwizard._
import org.http4s.server.middleware.Metrics

object MetricsRoute {
  implicit val clock = Clock.create[IO]

  val apiService = HttpRoutes.of[IO] {
    case GET -> Root / "api" => Ok()
    case GET -> Root / "metrics" => metricsResponse(registry)
  }

  val registry = SharedMetricRegistries.getOrCreate("default")

  val meteredRoutes = Metrics[IO](Dropwizard(registry, "server"))(apiService)
}
