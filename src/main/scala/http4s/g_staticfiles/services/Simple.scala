package http4s.g_staticfiles.services

import java.util.concurrent._
import cats.effect._
import org.http4s.server.staticcontent._

import scala.concurrent.ExecutionContext




object Simple {

  val blockingPool = Executors.newFixedThreadPool(4)
  val blocker = Blocker.liftExecutorService(blockingPool)

  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  val resourcePath: String = getClass.getResource("/").getPath
  val routes = fileService[IO](FileService.Config(s"$resourcePath/pages/index.html", blocker))

}
