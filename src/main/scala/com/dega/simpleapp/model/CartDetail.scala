package com.dega.simpleapp.model

import com.dega.simpleapp.Config
import com.dega.simpleapp.dao.BaseDao
import slick.driver.PostgresDriver.api._

/**
  * Author : surya (surya@degalab.com)
  */
object CartDetail extends CartDetailDao {

}

abstract class CartDetailExt {
    this: Tables#CartDetail =>

    private var _product: Option[Tables.Product] = None
    val product = {
        _product.getOrElse {
            Product.getById(productId) match {
                case Some(_p) => _p
                case _ =>
                    throw new Exception("no product with id " + id)
            }
        }
    }

    def setProduct(product: Tables.Product) = {
        _product = Some(product)
    }

    def updateTotal(_total: Int) = {
        Config.database.execute(Tables.CartDetails.map(_.total).update(total + _total))

    }

    def updatePrice(total: Int) = {
        val _price = product.price.toInt * total

        Config.database.execute(Tables.Carts.map(_.price).update(_price.toString))

    }

}

trait CartDetailDao extends BaseDao[Tables.CartDetailRow, Tables.CartDetail] {
    val tableReference = Tables.CartDetails

    def create(cartId:Long, productId:Long, total: Int) = {

        if (Cart.getById(cartId).isEmpty)
            throw new Exception("No cart with id " + cartId)

        if (Product.getById(productId).isEmpty)
            throw new Exception("No product with id " + productId)

        val query = tableReference.map(cd => (cd.cartId, cd.productId, cd.total))
            .returning(tableReference.map(_.id)) += (cartId, productId, total)

        val id = Config.database.execute(query)

        getById(id)

    }

    def updateTotalProduct(id: Long, total: Int) = {
        val query = tableReference.filter(_.id === id).map(x => x.total).update(total)

        Config.database.execute(query)
    }
}