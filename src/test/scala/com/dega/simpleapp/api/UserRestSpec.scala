package com.dega.simpleapp.api

import org.specs2.Specification
import org.specs2.matcher.JsonMatchers

/**
  * Author : surya (surya@degalab.com)
  */
class UserRestSpec extends Specification with RestapiTestHelper with JsonMatchers {

    def is = {
        sequential ^
        "User Rest API should" ^
        p ^
            "get list cart" ! trees.getListCart ^
        end
    }

    object trees {

        def getListCart = {
            get("api/me/cart?access_token=" + UtilRestHelper.dummyAccessToken) { resp =>
                resp.getResponseBody must /("code" -> 200)
                resp.getResponseBody must /("result" -> "success")
            }
        }
    }

}
