import sbt._
import Keys._
import com.earldouglas.xsbtwebplugin.WebPlugin


/**
  * Author : surya (surya@degalab.com)
  */

object Build extends Build {

    import BuildSettings._
    import Dependencies._

    lazy val root = Project("simpleapp", file("."))
        .settings(liftApp: _*)
        .settings(noPublishing: _*)
        .settings(
            libraryDependencies ++=
            compile(jodaTime, slick, slf4jLog, apacheCommonsCodec, dispatch) ++
            liftDeps ++
            test(specs2) ++
            runtime(logbackClassic, postgres)
        )

}