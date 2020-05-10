package http4s.c_middleware.middleware

import cats.effect._
import org.http4s._

object AddHeader {
  def addHeader(resp: Response[IO], header: Header) =
    resp match {
      case Status.Successful(resp) => resp.putHeaders(header)
      case resp => resp
    }

  def apply(service: HttpRoutes[IO], header: Header) =
    service.map(addHeader(_, header))
}