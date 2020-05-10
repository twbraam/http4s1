package http4s.b_DSL.services

import java.time.{LocalDate, Year}
import java.time.format.DateTimeFormatter

import cats.effect._
import org.http4s._
import org.http4s.dsl.io._

import scala.util.Try

object WeatherSimple {

  val df: DateTimeFormatter = DateTimeFormatter.ofPattern("d-MMM-yyyy")

  object LocalDateVar {
    def unapply(str: String): Option[LocalDate] = {
      if (!str.isEmpty)
        Try(LocalDate.parse(str, df)).toOption
      else
        None
    }
  }

  def getTemperatureForecast(date: LocalDate): IO[Double] = IO(42.23)

  val dailyWeatherService = HttpRoutes.of[IO] {
    case GET -> Root / "weather" / "temperature" / LocalDateVar(localDate) =>
      Ok(getTemperatureForecast(localDate).map(s"The temperature on $localDate will be: " + _))
  }
}