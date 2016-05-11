package com.dega.simpleapp.util

import org.slf4j.LoggerFactory

/**
  * Author : surya (surya@degalab.com)
  */
trait Loggers {

    private final lazy val log = LoggerFactory.getLogger(getClass)

    protected def debug(msg: String) = {
        log.debug(msg)
    }

    protected def info(msg: String) = {
        log.info(msg)
    }

    protected def warn(msg: String) = {
        log.warn(msg)
    }

    protected def error(msg: String) = {
        log.error(msg)
    }

}
