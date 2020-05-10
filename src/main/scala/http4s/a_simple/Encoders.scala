package http4s.a_simple

import cats.effect._
import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe._

object Encoders {
  implicit def tweetEncoder: EntityEncoder[IO, Tweet] = jsonEncoderOf[IO, Tweet]
  // tweetEncoder: org.http4s.EntityEncoder[cats.effect.IO,Tweet]

  implicit def tweetsEncoder: EntityEncoder[IO, Seq[Tweet]] = jsonEncoderOf[IO, Seq[Tweet]]
  // tweetsEncoder: org.http4s.EntityEncoder[cats.effect.IO,Seq[Tweet]]
}
