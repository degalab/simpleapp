package com.dega.simpleapp.database

import com.dega.simpleapp.util.Loggers
import slick.dbio.{Effect, NoStream}
import slick.driver.PostgresDriver
import PostgresDriver.api._
import slick.jdbc.meta.MTable

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Author : surya (surya@degalab.com)
  */
class PostgreSQL extends SimpleappDatabase with Loggers {

    /**
      * URL that used to connect to database
      */
    private val jdbcURL: String = "jdbc:postgresql://localhost/simpleapp?user=surya&password=test-app"

    /**
      * Buat variable db menjadi private untuk menghindari kejadian tak terduga.
      * Jadi hanya bisa melalui trait ini agar bisa mengakses variable db
      */
    lazy private val db:PostgresDriver.backend.DatabaseDef = {
        Database.forURL(jdbcURL, driver = "org.postgresql.Driver")
    }

    def close(): Unit = db.close()

    def execute[R, S <: NoStream, E <: Effect](action: DBIOAction[R, S, E]): R = {
        Await.result(db.run(action), 5.seconds)
    }

}
