name := "http4s1"

version := "0.1"

scalaVersion := "2.13.2"

val http4sVersion = "1.0.0-M0+195-b2e4ab6f-SNAPSHOT"
val circeVersion = "0.13.0"
val tsecVersion = "0.2.0"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "org.http4s"      %% "http4s-dsl"                % http4sVersion,
  "org.http4s"      %% "http4s-blaze-server"       % http4sVersion,
  "org.http4s"      %% "http4s-blaze-client"       % http4sVersion,
  "org.http4s"      %% "http4s-circe"              % http4sVersion,
  "io.circe"        %% "circe-generic"             % circeVersion,

  // metrics
  "org.http4s"      %% "http4s-server"             % http4sVersion,
  "org.http4s"      %% "http4s-dropwizard-metrics" % http4sVersion,

  // auth
  "org.reactormonk" %% "cryptobits"                % "1.3",
  "io.github.jmcardon" %% "tsec-common" % tsecVersion,
  "io.github.jmcardon" %% "tsec-cipher-jca" % tsecVersion,
  "io.github.jmcardon" %% "tsec-cipher-bouncy" % tsecVersion,
  "io.github.jmcardon" %% "tsec-jwt-mac" % tsecVersion,
  "io.github.jmcardon" %% "tsec-http4s" % tsecVersion
)