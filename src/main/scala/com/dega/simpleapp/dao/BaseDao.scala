package com.dega.simpleapp.dao

import com.dega.simpleapp.Config
import com.dega.simpleapp.model.Tables
import slick.lifted.TableQuery
import slick.driver.PostgresDriver.api._

/**
  * Author : surya (surya@degalab.com)
  */
trait BaseDao[T <: Tables.BaseTable[E], E <: Tables.Entity] {

    val tableReference: TableQuery[T]

    lazy private val queryId = tableQueryToTableQueryExtensionMethods(tableReference).findBy(_.id)

    def getById(id: Long): Option[T#TableElementType] = {
        Config.database.execute(queryId(id).result).headOption
    }

    def getList(offset: Int, limit: Int): Seq[T#TableElementType] = {
        val query = tableReference.drop(offset).take(limit).result

        Config.database.execute(query)
    }

    def delete(id: Long) {
        Config.database.execute(queryId(id).delete)
    }
}
