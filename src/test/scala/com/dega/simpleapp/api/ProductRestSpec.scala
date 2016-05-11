package com.dega.simpleapp.api

import org.specs2.Specification
import org.specs2.matcher.JsonMatchers

/**
  * Author : surya (surya@degalab.com)
  */
class ProductRestSpec extends Specification with RestapiTestHelper with JsonMatchers {

    def is = {
        sequential ^
        "Product Rest API should" ^
        p ^
            "get list product" ! trees.getListProduct ^
        end
    }

    object trees {

        def getListProduct = {
            get("api/product?offset=0&limit=10") { resp =>
                resp.getResponseBody must /("code" -> 200)
                resp.getResponseBody must /("result" -> "success")
            }
        }
    }

}
