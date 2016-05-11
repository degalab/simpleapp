package com.dega.simpleapp.model

import com.dega.simpleapp.dao.BaseDao
import com.dega.simpleapp.Config
import com.dega.simpleapp.util.DateUtils
import slick.driver.PostgresDriver.api._
import java.security.InvalidParameterException

/**
  * Author : surya (surya@degalab.com)
  */

object Product extends ProductDao

abstract class ProductExt {
    this: Tables#Product =>
}

trait ProductDao extends BaseDao[Tables.ProductRow, Tables.Product] {

    val tableReference = Tables.Products

    private val VALID_TITLE_RE = """[\w\s]+""".r

    def create(name: String, description: String, size: Option[String], color: Option[String], stock: Int, price: String) = {

        if (!isValidName(name))
            throw new InvalidParameterException("Title must min 3 and max 160 characters.")

        val query = tableReference.map(p => (p.name, p.description, p.size, p.color, p.stock, p.price, p.creationTime))
            .returning(tableReference.map(_.id)) += (name, description, size, color, stock, price, DateUtils.getTimestamp)

        val productId = Config.database.execute(query)

        getById(productId)
    }

    private def isValidName(str: String):Boolean = {
        str.length > 2 && str.length < 160 &&
            VALID_TITLE_RE.pattern.matcher(str).matches()
    }
}
