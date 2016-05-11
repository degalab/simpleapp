package com.dega.simpleapp.api

import com.ning.http.client.Response
import dispatch._

/**
  * Author : surya (surya@degalab.com)
  */
trait RestapiTestHelper {

    val http = new Http

    var version:String = "v1"

    protected def toAbsoluteUrl(endpoint:String) = {
        "http://localhost:8080/" + version + "/" + endpoint
    }

    def get(endpoint:String)( respHandler: Response => Unit ){
        respHandler {
            http(url(toAbsoluteUrl(endpoint))).apply()
        }
    }

}
