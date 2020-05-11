package http4s.h_entity.services

import cats.data._
import cats.effect._
import cats.implicits._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.headers.`Content-Type`


object Simple {
  sealed trait Resp
  case class Audio(body: String) extends Resp
  case class Video(body: String) extends Resp


  val response: IO[Response[IO]#Self] = Ok("").map(_.withContentType(`Content-Type`(MediaType.audio.ogg)))

  val audioDec: EntityDecoder[IO, Audio] = EntityDecoder.decodeBy(MediaType.audio.ogg) { (m: Media[IO]) =>
    EitherT {
      m.as[String].map(s => Audio(s).asRight[DecodeFailure])
    }
  }

  val videoDec: EntityDecoder[IO, Video] = EntityDecoder.decodeBy(MediaType.video.ogg) { (m: Media[IO]) =>
    EitherT {
      m.as[String].map(s => Video(s).asRight[DecodeFailure])
    }
  }

  implicit val bothDec: EntityDecoder[IO, Resp] = audioDec.widen[Resp] orElse videoDec.widen[Resp]


}
