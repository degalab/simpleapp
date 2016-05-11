import sbt._
import Keys._
import com.earldouglas.xsbtwebplugin.PluginKeys._
import com.earldouglas.xsbtwebplugin.WebPlugin._

/**
  * Author : surya (surya@degalab.com)
  */

object BuildSettings {

    lazy val basicSettings = Seq(
        version := "0.0.1",
        organization := "com.dega.simpleapp",
        scalaVersion := "2.11.7",
        scalacOptions := Seq("-deprecation", "-encoding", "utf8"),
        offline := true
    )

    lazy val liftApp = basicSettings ++ webSettings ++
        addCommandAlias("ccr", "~ ;container:start ;container:reload /") ++
        addCommandAlias("ccrs", "~ ;container:start ;container:reload / ;browserSync")


    lazy val noPublishing = Seq(
        publish :=(),
        publishLocal :=()
    )

}