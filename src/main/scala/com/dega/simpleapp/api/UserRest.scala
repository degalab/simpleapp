package com.dega.simpleapp.api

import UtilRestHelper._
import com.dega.simpleapp.model.Cart
import com.dega.simpleapp.util.{DateUtils, Loggers}
import DateUtils._
import net.liftweb.http.JsonResponse
import net.liftweb.http.rest.RestHelper
import net.liftweb.json.JsonDSL._
import net.liftweb.util.BasicTypesHelpers.AsLong

/**
  * Author : surya (surya@degalab.com)
  */
object UserRest extends RestHelper with Loggers {

    private val v1Str = "v1"

    serve {

        case `v1Str` :: "me" :: "cart" :: Nil Get _ => authorizationOnly() {
            case dummyOauthSession(userId) =>
                try {
                    val resp = Cart.getCartByUserId(userId).map { cart =>
                        success(
                            "entries" -> (
                                ("id" -> cart.id) ~
                                    ("creation_time" -> cart.creationTime.toStdFormat) ~
                                    ("price" -> cart.price) ~
                                    ("coupon" -> cart.couponCode) ~
                                    ("item" -> cart.cartDetail.map { cd =>
                                        ("id" -> cd.id) ~
                                            ("product_id" -> cd.productId) ~
                                            ("product_price" -> cd.product.price) ~
                                            ("total" -> cd.total)
                                    })
                                )
                        )
                    }.getOrElse(
                        ("result", "success") ~
                            ("data",
                                "entries" -> "Your cart is empty") ~
                            ("code", 200)
                    )

                    JsonResponse(resp)
                } catch {
                    case e: Exception =>
                        error(e.getMessage)
                        failedT(e.getMessage)
                }
        }

        case `v1Str` :: "me" :: "cart" :: AsLong(id) :: Nil Delete _ => authorizationOnly() {
            case dummyOauthSession(userId) =>
                try {
                    Cart.getCartByUserId(userId).map { cart =>
                        Cart.delete(cart.id)

                        successN
                    }.getOrElse(
                        throw new Exception("No cart with id " + id)
                    )
                } catch {
                    case e: Exception =>
                        error(e.getMessage)
                        failedT(e.getMessage)
                }
        }
    }
}
