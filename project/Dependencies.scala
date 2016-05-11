import sbt._

/**
  * Author : surya (surya@degalab.com)
  */

object Dependencies {

    val resolutionRepos = Seq(
        "typesafe repo" at "http://repo.typesafe.com/typesafe/release/",
        "Sonatype snapshot repo" at "https://oss.sonatype.org/content/repositories/snapshots",
        "Sonatype releases"      at "https://oss.sonatype.org/content/repositories/releases"
    )

    def compile   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
    def provided  (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
    def test      (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
    def runtime   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
    def container (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

    val specs2         = "org.specs2" %%  "specs2" % "2.3.11"
    val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.0.9"
    lazy val apacheCommonsCodec = "commons-codec" % "commons-codec" % "1.10"
    lazy val slf4jLog = "org.slf4j" % "slf4j-api" % "1.7.21"
    lazy val dispatch = "net.databinder.dispatch" % "dispatch-core_2.10" % "0.11.0"

    val liftVersion = "2.6.2"
    lazy val liftDeps = {
        Seq(
            liftWebkit % "compile",
            "net.liftweb"       %% "lift-mapper"        % liftVersion        % "compile",
            "net.liftweb"       %% "lift-wizard"        % liftVersion        % "compile",
            "net.liftmodules"   %% "lift-jquery-module_2.6" % "2.8",
            "org.eclipse.jetty" % "jetty-webapp"        % "9.2.9.v20150224"  % "container",
            "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
            "ch.qos.logback"    % "logback-classic"     % "1.0.13",
            jettyServer
        )
    }
    lazy val liftWebkit = "net.liftweb"  %% "lift-webkit" % liftVersion
    lazy val liftDepsNoContainer = liftDeps.filter(x => !(x.name.endsWith("webapp") || x.name.endsWith("servlet")))

    lazy val jodaTime   = "joda-time" % "joda-time" % "2.1"
    lazy val slick = "com.typesafe.slick" % "slick_2.11" % "3.1.1"
    lazy val postgres = "org.postgresql" % "postgresql" % "9.4.1208.jre7"
    lazy val jettyServer = "org.eclipse.jetty" % "jetty-server" % "8.1.17.v20150415"

}