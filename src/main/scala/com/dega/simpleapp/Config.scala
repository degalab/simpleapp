package com.dega.simpleapp

import com.dega.simpleapp.database.{SimpleappDatabase, PostgreSQL}
import com.dega.simpleapp.util.Loggers

/**
  * Author : surya (surya@degalab.com)
  */
object Config extends Loggers {


    var database:SimpleappDatabase = _

    def setup() = {
        debug("Initialize database")

        database = new PostgreSQL
    }

}
