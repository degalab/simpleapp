package com.dega.simpleapp.api

import java.security.InvalidParameterException

import UtilRestHelper._
import com.dega.simpleapp.util.Loggers
import com.dega.simpleapp.model.{Cart, CartDetail, Product}
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.S
import net.liftweb.util.BasicTypesHelpers.AsLong
import net.liftweb.json.JsonDSL._

/**
  * Author : surya (surya@degalab.com)
  */
object ProductRest extends RestHelper with Loggers {

    private val v1Str = "v1"
    private val productStr = "product"

    serve {

        case `v1Str` :: `productStr` :: Nil Get _ =>

            try {

                val limit = S.param("limit").getOrElse{
                    throw new InvalidParameterException("param limit is required")
                }.toInt

                val offset = S.param("offset").getOrElse{
                    throw new InvalidParameterException("param offset is required")
                }.toInt

                success(
                    "entries" -> Product.getList(offset, limit).map { p =>
                        ("id" -> p.id) ~
                            ("name" -> p.name) ~
                            ("description" -> p.description) ~
                            ("color" -> p.color) ~
                            ("size" -> p.size) ~
                            ("stock" -> p.stock) ~
                            ("price" -> p.price)
                    }
                )
            } catch {
                case e: Exception =>
                    error(e.getMessage)
                    failedT(e.getMessage)
            }

        case `v1Str` :: `productStr` :: AsLong(id) :: Nil Get _ =>

            try {

                val product = Product.getById(id).getOrElse(
                    throw new ClassNotFoundException("no product with id " + id)
                )

                success(
                    "entries" -> (
                        ("id" -> product.id) ~
                            ("name" -> product.name) ~
                            ("description" -> product.description) ~
                            ("color" -> product.color) ~
                            ("size" -> product.size) ~
                            ("stock" -> product.stock) ~
                            ("price" -> product.price)
                        )
                )

            } catch {
                case e: Exception =>
                    error(e.getMessage)
                    failedT(e.getMessage)
            }

        case `v1Str` :: `productStr` :: Nil Post req => authorizationOnly() {
            case dummyOauthSession(userId) =>
                try {

                    println(req.param("name"))
                    val name = S.param("name").getOrElse(
                        throw new InvalidParameterException("param name is required")
                    )

                    val description = S.param("description").getOrElse(
                        throw new InvalidParameterException("param description is required")
                    )

                    val stock = S.param("stock").getOrElse(
                        throw new InvalidParameterException("param stock is required")
                    )

                    val price = S.param("price").getOrElse(
                        throw new InvalidParameterException("param price is required")
                    )

                    val size = S.param("size").getOrElse("")
                    val color = S.param("color").getOrElse("")

                    val product = Product.create(name, description, Some(size), Some(color), stock.toInt, price)

                    success(
                        "entries" -> product.map { p =>
                            ("id" -> p.id) ~
                                ("name" -> p.name) ~
                                ("description" -> p.description) ~
                                ("color" -> p.color) ~
                                ("size" -> p.size) ~
                                ("stock" -> p.stock) ~
                                ("price" -> p.price) ~
                                ("creation_time" -> p.creationTime.toString)
                        }
                    )
                } catch {
                    case e: Exception =>
                        error(e.getMessage)
                        failedT(e.getMessage)
                }
        }

        case `v1Str` :: `productStr` :: AsLong(id) :: Nil Delete _ => authorizationOnly() {
            case dummyOauthSession(userId) =>
                try {

                    Product.delete(id)

                    successN
                } catch {
                    case e: Exception =>
                        error(e.getMessage)
                        failedT(e.getMessage)
                }
        }

        case `v1Str` :: `productStr` :: AsLong(productId) :: "add-to-cart" :: Nil Post _ => authorizationOnly() {
            case dummyOauthSession(id) =>
                try {
                    val total = S.param("total").getOrElse("1").toInt

                    Product.getById(productId).map { product =>
                        val price = product.price.toInt * total
                        Cart.getCartByUserId(id).map { cart =>
                            // update cart detail
                            val _price = cart.price.toInt + price
                            cart.cartDetail.find(_.productId == productId).map { cd =>
                                // update total product
                                cd.updateTotal(total)
                            }.getOrElse(
                                CartDetail.create(cart.id, productId, total)
                            )

                            // update price to cart
                            cart.updatePrice(_price.toString)

                        }.getOrElse {
                            val cart = Cart.create(id, Some(""), price.toString)

                            // add to cart detail
                            CartDetail.create(cart.get.id, productId, total)

                        }

                        successN
                    }.getOrElse(
                        throw new Exception("No product with id " + productId)
                    )
                } catch {
                    case e: Exception =>
                        error(e.getMessage)
                        failedT(e.getMessage)
                }
        }
    }
}
