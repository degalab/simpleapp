package com.dega.simpleapp.model

import java.security.InvalidParameterException

import com.dega.simpleapp.Config
import com.dega.simpleapp.dao.BaseDao
import com.dega.simpleapp.util.DateUtils
import slick.driver.PostgresDriver.api._

/**
  * Author : surya (surya@degalab.com)
  */
object Coupon extends CouponDao {

    private val queryByCode = tableQueryToTableQueryExtensionMethods(tableReference).findBy(_.couponCode)

    def getByCode(code: String) = {
        Config.database.execute(queryByCode(code).result).headOption
    }
}

abstract class CouponExt {
    this: Tables#Coupon =>

}

trait CouponDao extends BaseDao[Tables.CouponRow, Tables.Coupon] {

    val tableReference = Tables.Coupons

    private val VALID_CODE_RE = """[A-Za-z0-9]+""".r

    def create(code:String, description:String, valid:Int, price:String) = {

        if (!isValidCode(code))
            throw new InvalidParameterException("Invalid coupon code format. Only alphanumeric")

        val query = Tables.Coupons.map(c => (c.couponCode, c.description, c.valid, c.creationTime, c.price))
            .returning(Tables.Coupons.map(_.id)) += (code, description, valid, DateUtils.getTimestamp, price)

        val id = Config.database.execute(query)

        getById(id)
    }

    private def isValidCode(str: String): Boolean = {
        VALID_CODE_RE.pattern.matcher(str).matches()
    }

}
