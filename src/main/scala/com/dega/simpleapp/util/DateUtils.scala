package com.dega.simpleapp.util

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

import org.joda.time.DateTime

/**
  * Author : surya (surya@degalab.com)
  */
object DateUtils {

    val dateTime = new DateTime()
    private val stdFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm")

    def nowMillis = dateTime.getMillis

    def getTimestamp =  new Timestamp(nowMillis)

    class WrapDate(d: Date) {
        def toStdFormat = stdFormat.format(d)
    }

    implicit def wrapDateUtils(d: Date) = new WrapDate(d)

}
