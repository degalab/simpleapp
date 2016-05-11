package com.dega.simpleapp.api

import com.dega.simpleapp.util.Loggers
import com.dega.simpleapp.model.Coupon
import UtilRestHelper._
import java.security.InvalidParameterException
import net.liftweb.http.S
import net.liftweb.http.rest.RestHelper
import net.liftweb.json.JsonDSL._
import net.liftweb.util.BasicTypesHelpers.AsLong

/**
  * Author : surya (surya@degalab.com)
  */
object CouponRest extends RestHelper with Loggers {

    private val v1Str = "v1"
    private val couponStr = "coupon"

    serve {

        case `v1Str` :: `couponStr` :: Nil Get _ =>
            try {

                val limit = S.param("limit").getOrElse{
                    throw new InvalidParameterException("param limit is required")
                }.toInt

                val offset = S.param("offset").getOrElse{
                    throw new InvalidParameterException("param offset is required")
                }.toInt

                success(
                    "entries" -> Coupon.getList(offset, limit).map { c =>
                        ("id" -> c.id) ~
                            ("coupon_code" -> c.couponCode) ~
                            ("description" -> c.description) ~
                            ("discount" -> c.price) ~
                            ("is_valid" -> c.valid)
                    }
                )
            } catch {
                case e:Exception =>
                    error(e.getMessage)
                    failedT(e.getMessage)
            }

        case `v1Str` :: `couponStr` :: AsLong(id) :: Nil Get _ =>
            try {
                val coupon = Coupon.getById(id).getOrElse(
                    throw new ClassNotFoundException("no coupon with id " + id)
                )

                success(
                    "entries" -> (
                        ("id" -> coupon.id) ~
                            ("coupon_code" -> coupon.couponCode) ~
                            ("description" -> coupon.description) ~
                            ("discount" -> coupon.price) ~
                            ("is_valid" -> coupon.valid)
                        )
                )
            } catch {
                case e:Exception =>
                    error(e.getMessage)
                    failedT(e.getMessage)
            }

        case `v1Str` :: `couponStr` :: Nil Post req => authorizationOnly() {
            case dummyOauthSession(userId) =>
                try {

                    val couponCode = S.param("coupon_code").getOrElse(
                        throw new InvalidParameterException("no `coupon_code` param")
                    )

                    val description = S.param("description").getOrElse(
                        throw new InvalidParameterException("no `description` param")
                    )

                    val price = S.param("price").getOrElse(
                        throw new InvalidParameterException("no `price` param")
                    )

                    val valid = S.param("is_valid").getOrElse("1").toInt

                    val coupon = Coupon.create(couponCode, description, valid, price)

                    success(
                        "entries" -> coupon.map { c =>
                            ("id" -> c.id) ~
                                ("coupon_code" -> c.couponCode) ~
                                ("description" -> c.description) ~
                                ("discount" -> c.price) ~
                                ("is_valid" -> c.valid)
                        }
                    )
                } catch {
                    case e: Exception =>
                        error(e.getMessage)
                        failedT(e.getMessage)
                }
        }

        case `v1Str` :: `couponStr` :: AsLong(id) :: Nil Delete req => authorizationOnly() {
            case dummyOauthSession(userId) =>
                try {

                    Coupon.delete(id)

                    successN
                } catch {
                    case e: Exception =>
                        error(e.getMessage)
                        failedT(e.getMessage)
                }
        }
    }

}
