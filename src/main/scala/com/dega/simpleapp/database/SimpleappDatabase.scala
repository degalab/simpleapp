package com.dega.simpleapp.database

import slick.driver.PostgresDriver.api._

/**
  * Author : surya (surya@degalab.com)
  */
abstract class SimpleappDatabase {

    def close()

    def execute[R, S <: slick.dbio.NoStream, E <: slick.dbio.Effect](action: DBIOAction[R, S, E]): R

}
