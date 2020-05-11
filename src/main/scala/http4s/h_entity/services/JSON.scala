package http4s.h_entity.services

import cats.effect._
import io.circe._
import io.circe.literal._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.circe._
import org.http4s.client._
import org.http4s.client.dsl.io._
import io.circe.syntax._
import io.circe.generic.auto._



object JSON {

  case class Hello(hello: String)
  case class User(name: String)

  implicit val userDecoder = jsonOf[IO, User]

  val routes = HttpRoutes.of[IO] {
    case GET -> Root =>
      Ok(Hello("Alice").asJson)
    case req @ POST -> Root / "hello" =>
      for {
        user <- req.as[User]
        resp <- Ok(Hello(user.name).asJson)
      } yield resp
  }

  // curl --header "Content-Type: application/json" \
  //  --data '{"name":"Alice"}' \
  //  http://localhost:8080/hello
}
