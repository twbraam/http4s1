package http4s.b_DSL.services

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, Year}

import cats.effect._
import org.http4s._
import org.http4s.dsl.io._

import scala.util.Try

object WeatherValidated {

  val df: DateTimeFormatter = DateTimeFormatter.ofPattern("d-MMM-yyyy")

  object LocalDateVar {
    def unapply(str: String): Option[LocalDate] = {
      if (!str.isEmpty)
        Try(LocalDate.parse(str, df)).toOption
      else
        None
    }
  }

  object CountryQueryParamMatcher extends QueryParamDecoderMatcher[String]("country")
  object YearQueryParamMatcher extends ValidatingQueryParamDecoderMatcher[Year]("year")

  implicit val yearQueryParamDecoder: QueryParamDecoder[Year] =
    QueryParamDecoder[Int].map(Year.of)

  def getAverageTemperatureForCountryAndYear(country: String, year: Year): IO[Double] = IO(99.99)

  val routes = HttpRoutes.of[IO] {
    case GET -> Root / "temperature" :? CountryQueryParamMatcher(country) +& YearQueryParamMatcher(yearValidated)  =>
      yearValidated.fold(
        _ => BadRequest("unable to parse argument year"),
        year => Ok(getAverageTemperatureForCountryAndYear(country, year).map(s"Average temperature for $country in $year was: " + _))
      )

  }

}