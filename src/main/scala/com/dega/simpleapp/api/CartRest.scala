package com.dega.simpleapp.api

import UtilRestHelper._
import com.dega.simpleapp.model.{Cart, CartDetail, Coupon}
import com.dega.simpleapp.util.Loggers
import net.liftweb.http.S
import net.liftweb.http.rest.RestHelper
import net.liftweb.util.BasicTypesHelpers.AsLong

/**
  * Author : surya (surya@degalab.com)
  */
object CartRest extends RestHelper with Loggers {

    private val v1Str = "v1"
    private val cartStr = "cart"

    serve {

        case `v1Str` :: `cartStr` :: AsLong(cartId) :: "add-coupon" :: Nil Post _ => authorizationOnly() {
            case dummyOauthSession(userId) =>
                try {
                    val couponCode = S.param("coupon_code").getOrElse(
                        throw new Exception("invalid coupon code")
                    )

                    Cart.getById(cartId).map { cart =>
                        Coupon.getByCode(couponCode).map { cp =>
                            if (cart.hasCouponCode)
                                throw new Exception("you already applied the coupon code")

                            if (cp.valid == 0)
                                throw new Exception("coupon invalid")

                            val _price = cart.price.toInt - cp.price.toInt

                            cart.updatePrice(_price.toString)
                            cart.updateCouponCode(couponCode)
                        }.getOrElse(
                            throw new Exception(s"coupon code $couponCode not found")
                        )

                        successN
                    }.getOrElse(
                        throw new Exception("Cart not found with id " + cartId)
                    )
                } catch {
                    case e: Exception =>
                        error(e.getMessage)
                        failedT(e.getMessage)
                }
        }

        case `v1Str` :: `cartStr` :: AsLong(cartId) :: "remove-item" :: AsLong(itemId) :: Nil Post _ => authorizationOnly() {
            case dummyOauthSession(userId) =>
                try {
                    Cart.getById(cartId).map { cart =>
                        if (cart.cartDetail.length == 1) {
                            // delete it if only 1 cart item left
                            Cart.delete(cart.id)
                        } else {
                            cart.cartDetail.find(_.id == itemId).map { cd =>
                                val price = cd.product.price.toInt * cd.total
                                val _price = cart.price.toInt - price

                                cart.updatePrice(_price.toString)
                                CartDetail.delete(itemId)
                            }.getOrElse(
                                throw new Exception("this item doesn't in your cart")
                            )
                        }

                        successN
                    }.getOrElse(
                        throw new Exception("No cart with id " + cartId)
                    )
                } catch {
                    case e: Exception =>
                        error(e.getMessage)
                        failedT(e.getMessage)
                }
        }
    }

}
