package com.dega.simpleapp.model

import com.dega.simpleapp.Config
import com.dega.simpleapp.dao.BaseDao
import com.dega.simpleapp.util.DateUtils
import slick.driver.PostgresDriver.api._
import org.apache.commons.codec.digest.DigestUtils

/**
  * Author : surya (surya@degalab.com)
  */
object Users extends UsersDao {

    private val queryByNickname = tableQueryToTableQueryExtensionMethods(tableReference).findBy(_.nickname)
    private val queryByEmail = tableQueryToTableQueryExtensionMethods(tableReference).findBy(_.email)

    def getUserByCredential(nickname:String, password:String) = {

        val _pass = DigestUtils.sha1Hex(password + salt)
        val query = Tables.Userses.filter(u => u.nickname === nickname && u.pass === _pass).take(1)

        Config.database.execute(query.result).headOption

    }

    def getByNickname(nickname:String) = {
        Config.database.execute(queryByNickname(nickname).result).headOption
    }


    def getByEmail(email:String) = {
        Config.database.execute(queryByEmail(email).result).headOption
    }
}

abstract class UsersExt {
    this: Tables#Users =>

    val cart = for {
        _cart <- Tables.Carts.filter(_.userId === id)
        user <- Tables.Userses.filter(_.id === _cart.userId)
    } yield _cart

}

trait UsersDao extends BaseDao[Tables.UsersRow, Tables.Users] {

    val tableReference = Tables.Userses

    protected val salt = "m];>KBlv2\"+E<$S)v?8.k=#KBd7I20=BFvy3O200:oK>5-#DM)P8h/n%Z7Q0mW9zYS0SL5=3K,m!${?3?l+f\\'9jp>VF-X){87nj2lf!{gMJ,XdX~283]0[qc]4_fJVh,L0654[@83p3~2,T9h<(I4]NE5-6H4ethwwpv@M84l.fu3{``!!2|hvtfxgeqZJdXh<X6rFOQL{9z+W89s)"

    def isNicknameExists(nickname: String): Boolean = {
        Users.getByNickname(nickname).nonEmpty
    }

    def isEmailExists(email: String): Boolean = {
        Users.getByEmail(email).nonEmpty
    }

    def create(nickname:String, email:String, pass:String) = {

        if (!isNicknameExists(nickname))
            throw new Exception("Nickname already exists")

        if (!isEmailExists(email))
            throw new Exception("Email already exists")

        val _pass = DigestUtils.sha1Hex(pass + salt)
        val query = Tables.Userses.map(u => (u.nickname, u.email, u.pass, u.creationTime))
            .returning(Tables.Userses.map(_.id)) += (nickname, email, _pass, DateUtils.getTimestamp)

        val userId = Config.database.execute(query)

        getById(userId)

    }

    override def getList(offset: Int, limit: Int) = Seq.empty
}