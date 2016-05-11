package com.dega.simpleapp.model

import java.security.InvalidParameterException

import com.dega.simpleapp.Config
import com.dega.simpleapp.dao.BaseDao
import com.dega.simpleapp.util.{DateUtils, Loggers}
import slick.driver.PostgresDriver.api._

/**
  * Author : surya (surya@degalab.com)
  */
object Cart extends CartDao {

    private val queryByUid = tableQueryToTableQueryExtensionMethods(Tables.Carts).findBy(_.userId)

    def getCartByUserId(uid: Long) = {
        Config.database.execute(queryByUid(uid).result).headOption
    }

}

abstract class CartExt extends Loggers {
    this: Tables#Cart =>

    val cartDetail = {
        val q = for {
            cd <- Tables.CartDetails.filter(_.cartId === id)
            cart <- Tables.Carts.filter(_.id === cd.cartId)
        } yield cd

        Config.database.execute(q.result)
    }

    def updatePrice(price: String) = {
        Config.database.execute(Tables.Carts.map(_.price).update(price))
    }

    def updateCouponCode(code: String) = {
        if (Coupon.getByCode(code).isEmpty)
            throw new Exception(s"coupon code $code not found")

        Config.database.execute(Tables.Carts.map(_.couponCode).update(Some(code)))
    }

    def hasCouponCode:Boolean = couponCode.getOrElse("").nonEmpty

}

trait CartDao extends BaseDao[Tables.CartRow, Tables.Cart] {
    val tableReference = Tables.Carts

    def create(uid: Long, couponCode: Option[String] = None, price: String) = {

        if (Users.getById(uid).isEmpty)
            throw new InvalidParameterException("No user with id " + uid)

        val query = tableReference.map(c => (c.userId, c.couponCode, c.price, c.creationTime))
            .returning(tableReference.map(_.id)) += (uid, couponCode, price, DateUtils.getTimestamp)

        val id = Config.database.execute(query)

        getById(id)

    }

    override def getList(offset: Int, limit: Int) = Seq.empty
}