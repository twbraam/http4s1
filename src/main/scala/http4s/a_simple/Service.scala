package http4s.a_simple

import cats.effect._
import http4s.a_simple.Encoders._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._

import scala.concurrent.ExecutionContext.Implicits.global


object Service {

  implicit val cs: ContextShift[IO] = IO.contextShift(global)
  implicit val timer: Timer[IO] = IO.timer(global)

  val helloWorldService = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name => Ok(s"Hello, $name.")
  }

  //////////////////////////////////////////////////////////////////////////////////

  def getTweet(tweetId: Int): IO[Tweet] = IO(Tweet(tweetId, s"this is tweet $tweetId"))

  def getPopularTweets: IO[Seq[Tweet]] = IO((1 to 10).map { i =>
    Tweet(i, s"this is tweet $i")
  })

  val tweetService = HttpRoutes.of[IO] {
    case GET -> Root / "tweets" / "popular" =>
      getPopularTweets.flatMap(Ok(_))
    case GET -> Root / "tweets" / IntVar(tweetId) =>
      getTweet(tweetId).flatMap(Ok(_))
  }

}
