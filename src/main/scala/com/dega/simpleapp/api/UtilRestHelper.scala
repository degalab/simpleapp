package com.dega.simpleapp.api

import com.dega.simpleapp.model.Users
import com.dega.simpleapp.util.Loggers
import java.security.InvalidParameterException
import net.liftweb.json._
import JsonDSL._
import net.liftweb.http.{JsonResponse, LiftResponse, ResponseShortcutException, S}

/**
  * Author : surya (surya@degalab.com)
  */
object UtilRestHelper extends Loggers{

    val dummyAccessToken = "O7qcOGReTMgXRt_UZGw8gJilRKKvpDzt9639265022Z5Wa7TH8HzgHnq.BKb1_yGSbxYT-hakm9639265023owJTcgm-OE5m-Z6ZTMR_IZ7ATKhEQZr19639265024"

    def success(data:JValue) = ("result", "success") ~ ("data", data) ~ ("code", 200)
    def successN = ("result", "success") ~ ("code", 200)
    def failedT(message:String) = ("result", "failed") ~ ("message", message) ~ ("code", 500)

    def authorizationOnly()(func: (OAuthSession) => LiftResponse):LiftResponse = {
        try {
            val at = S.param("access_token").getOrElse(
                throw new InvalidParameterException("No `access_token` param")
            )

            if (at != dummyAccessToken) {
                throw new Exception("Invalid Access Token")
            }

            Users.getByNickname("dummy").map{ user =>
                func(dummyOauthSession(user.id))
            }.getOrElse(
                throw new Exception("user dummy not found")
            )

        } catch {
            case e:ResponseShortcutException =>
                throw e
            case e: Exception =>
                error(e.getMessage)
                JsonResponse(failedT(e.getMessage))
        }
    }

}

abstract class OAuthSession

case class dummyOauthSession(userId: Long) extends OAuthSession
