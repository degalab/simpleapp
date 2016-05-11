import sbt._

resolvers ++= Seq(
    "typesafe repo" at "http://repo.typesafe.com/typesafe/release/"
)

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "0.9.0")
