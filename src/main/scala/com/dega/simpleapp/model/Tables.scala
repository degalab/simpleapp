package com.dega.simpleapp.model
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}
  // base entity class
  trait Entity

  abstract class BaseTable[T](tag: Tag, name: String) extends Table[T](tag, name) {
      val id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
  }
  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Carts.schema ++ CartDetails.schema ++ Coupons.schema ++ Products.schema ++ Userses.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Cart
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param userId Database column user_id SqlType(int8)
   *  @param couponCode Database column coupon_code SqlType(varchar), Length(10,true), Default(None)
   *  @param price Database column price SqlType(varchar), Length(55,true)
   *  @param creationTime Database column creation_time SqlType(timestamp) */
  case class Cart(id: Long, userId: Long, couponCode: Option[String] = None, price: String, creationTime: java.sql.Timestamp) extends CartExt with Entity
  /** GetResult implicit for fetching Cart objects using plain SQL queries */
  implicit def GetResultCart(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[String], e3: GR[java.sql.Timestamp]): GR[Cart] = GR{
    prs => import prs._
    Cart.tupled((<<[Long], <<[Long], <<?[String], <<[String], <<[java.sql.Timestamp]))
  }
  /** Table description of table cart. Objects of this class serve as prototypes for rows in queries. */
  class CartRow(_tableTag: Tag) extends BaseTable[Cart](_tableTag, "cart") {
    def * = (id, userId, couponCode, price, creationTime) <> (Cart.tupled, Cart.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userId), couponCode, Rep.Some(price), Rep.Some(creationTime)).shaped.<>({r=>import r._; _1.map(_=> Cart.tupled((_1.get, _2.get, _3, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    override val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(int8) */
    val userId: Rep[Long] = column[Long]("user_id")
    /** Database column coupon_code SqlType(varchar), Length(10,true), Default(None) */
    val couponCode: Rep[Option[String]] = column[Option[String]]("coupon_code", O.Length(10,varying=true), O.Default(None))
    /** Database column price SqlType(varchar), Length(55,true) */
    val price: Rep[String] = column[String]("price", O.Length(55,varying=true))
    /** Database column creation_time SqlType(timestamp) */
    val creationTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("creation_time")

    /** Foreign key referencing Users (database name cart_user_id_fkey) */
    lazy val usersFk = foreignKey("cart_user_id_fkey", userId, Userses)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Cart */
  lazy val Carts = new TableQuery(tag => new CartRow(tag))

  /** Entity class storing rows of table CartDetail
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param cartId Database column cart_id SqlType(int8)
   *  @param productId Database column product_id SqlType(int8)
   *  @param total Database column total SqlType(int4), Default(1) */
  case class CartDetail(id: Long, cartId: Long, productId: Long, total: Int = 1) extends CartDetailExt with Entity
  /** GetResult implicit for fetching CartDetail objects using plain SQL queries */
  implicit def GetResultCartDetail(implicit e0: GR[Long], e1: GR[Int]): GR[CartDetail] = GR{
    prs => import prs._
    CartDetail.tupled((<<[Long], <<[Long], <<[Long], <<[Int]))
  }
  /** Table description of table cart_detail. Objects of this class serve as prototypes for rows in queries. */
  class CartDetailRow(_tableTag: Tag) extends BaseTable[CartDetail](_tableTag, "cart_detail") {
    def * = (id, cartId, productId, total) <> (CartDetail.tupled, CartDetail.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(cartId), Rep.Some(productId), Rep.Some(total)).shaped.<>({r=>import r._; _1.map(_=> CartDetail.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    override val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column cart_id SqlType(int8) */
    val cartId: Rep[Long] = column[Long]("cart_id")
    /** Database column product_id SqlType(int8) */
    val productId: Rep[Long] = column[Long]("product_id")
    /** Database column total SqlType(int4), Default(1) */
    val total: Rep[Int] = column[Int]("total", O.Default(1))

    /** Foreign key referencing Cart (database name cart_detail_cart_id_fkey) */
    lazy val cartFk = foreignKey("cart_detail_cart_id_fkey", cartId, Carts)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Product (database name cart_detail_product_id_fkey) */
    lazy val productFk = foreignKey("cart_detail_product_id_fkey", productId, Products)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table CartDetail */
  lazy val CartDetails = new TableQuery(tag => new CartDetailRow(tag))

  /** Entity class storing rows of table Coupon
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param couponCode Database column coupon_code SqlType(varchar), Length(10,true)
   *  @param description Database column description SqlType(varchar), Length(150,true)
   *  @param valid Database column valid SqlType(int4), Default(1)
   *  @param price Database column price SqlType(varchar), Length(255,true)
   *  @param creationTime Database column creation_time SqlType(timestamp) */
  case class Coupon(id: Long, couponCode: String, description: String, valid: Int = 1, price: String, creationTime: java.sql.Timestamp) extends CouponExt with Entity
  /** GetResult implicit for fetching Coupon objects using plain SQL queries */
  implicit def GetResultCoupon(implicit e0: GR[Long], e1: GR[String], e2: GR[Int], e3: GR[java.sql.Timestamp]): GR[Coupon] = GR{
    prs => import prs._
    Coupon.tupled((<<[Long], <<[String], <<[String], <<[Int], <<[String], <<[java.sql.Timestamp]))
  }
  /** Table description of table coupon. Objects of this class serve as prototypes for rows in queries. */
  class CouponRow(_tableTag: Tag) extends BaseTable[Coupon](_tableTag, "coupon") {
    def * = (id, couponCode, description, valid, price, creationTime) <> (Coupon.tupled, Coupon.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(couponCode), Rep.Some(description), Rep.Some(valid), Rep.Some(price), Rep.Some(creationTime)).shaped.<>({r=>import r._; _1.map(_=> Coupon.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    override val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column coupon_code SqlType(varchar), Length(10,true) */
    val couponCode: Rep[String] = column[String]("coupon_code", O.Length(10,varying=true))
    /** Database column description SqlType(varchar), Length(150,true) */
    val description: Rep[String] = column[String]("description", O.Length(150,varying=true))
    /** Database column valid SqlType(int4), Default(1) */
    val valid: Rep[Int] = column[Int]("valid", O.Default(1))
    /** Database column price SqlType(varchar), Length(255,true) */
    val price: Rep[String] = column[String]("price", O.Length(255,varying=true))
    /** Database column creation_time SqlType(timestamp) */
    val creationTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("creation_time")

    /** Uniqueness Index over (couponCode) (database name coupon_code_unique_index) */
    val index1 = index("coupon_code_unique_index", couponCode, unique=true)
  }
  /** Collection-like TableQuery object for table Coupon */
  lazy val Coupons = new TableQuery(tag => new CouponRow(tag))

  /** Entity class storing rows of table Product
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(varchar), Length(160,true)
   *  @param description Database column description SqlType(text)
   *  @param size Database column size SqlType(varchar), Length(4,true), Default(None)
   *  @param color Database column color SqlType(varchar), Length(20,true), Default(None)
   *  @param stock Database column stock SqlType(int4), Default(1)
   *  @param price Database column price SqlType(varchar), Length(50,true)
   *  @param creationTime Database column creation_time SqlType(timestamp) */
  case class Product(id: Long, name: String, description: String, size: Option[String] = None, color: Option[String] = None, stock: Int = 1, price: String, creationTime: java.sql.Timestamp) extends ProductExt with Entity
  /** GetResult implicit for fetching Product objects using plain SQL queries */
  implicit def GetResultProduct(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]], e3: GR[Int], e4: GR[java.sql.Timestamp]): GR[Product] = GR{
    prs => import prs._
    Product.tupled((<<[Long], <<[String], <<[String], <<?[String], <<?[String], <<[Int], <<[String], <<[java.sql.Timestamp]))
  }
  /** Table description of table product. Objects of this class serve as prototypes for rows in queries. */
  class ProductRow(_tableTag: Tag) extends BaseTable[Product](_tableTag, "product") {
    def * = (id, name, description, size, color, stock, price, creationTime) <> (Product.tupled, Product.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(description), size, color, Rep.Some(stock), Rep.Some(price), Rep.Some(creationTime)).shaped.<>({r=>import r._; _1.map(_=> Product.tupled((_1.get, _2.get, _3.get, _4, _5, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    override val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(varchar), Length(160,true) */
    val name: Rep[String] = column[String]("name", O.Length(160,varying=true))
    /** Database column description SqlType(text) */
    val description: Rep[String] = column[String]("description")
    /** Database column size SqlType(varchar), Length(4,true), Default(None) */
    val size: Rep[Option[String]] = column[Option[String]]("size", O.Length(4,varying=true), O.Default(None))
    /** Database column color SqlType(varchar), Length(20,true), Default(None) */
    val color: Rep[Option[String]] = column[Option[String]]("color", O.Length(20,varying=true), O.Default(None))
    /** Database column stock SqlType(int4), Default(1) */
    val stock: Rep[Int] = column[Int]("stock", O.Default(1))
    /** Database column price SqlType(varchar), Length(50,true) */
    val price: Rep[String] = column[String]("price", O.Length(50,varying=true))
    /** Database column creation_time SqlType(timestamp) */
    val creationTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("creation_time")

    /** Uniqueness Index over (name) (database name product_name_unique_index) */
    val index1 = index("product_name_unique_index", name, unique=true)
  }
  /** Collection-like TableQuery object for table Product */
  lazy val Products = new TableQuery(tag => new ProductRow(tag))

  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param nickname Database column nickname SqlType(varchar), Length(255,true)
   *  @param email Database column email SqlType(varchar), Length(155,true)
   *  @param pass Database column pass SqlType(varchar), Length(255,true)
   *  @param creationTime Database column creation_time SqlType(timestamp) */
  case class Users(id: Long, nickname: String, email: String, pass: String, creationTime: java.sql.Timestamp) extends UsersExt with Entity
  /** GetResult implicit for fetching Users objects using plain SQL queries */
  implicit def GetResultUsers(implicit e0: GR[Long], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[Users] = GR{
    prs => import prs._
    Users.tupled((<<[Long], <<[String], <<[String], <<[String], <<[java.sql.Timestamp]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class UsersRow(_tableTag: Tag) extends BaseTable[Users](_tableTag, "users") {
    def * = (id, nickname, email, pass, creationTime) <> (Users.tupled, Users.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(nickname), Rep.Some(email), Rep.Some(pass), Rep.Some(creationTime)).shaped.<>({r=>import r._; _1.map(_=> Users.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    override val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column nickname SqlType(varchar), Length(255,true) */
    val nickname: Rep[String] = column[String]("nickname", O.Length(255,varying=true))
    /** Database column email SqlType(varchar), Length(155,true) */
    val email: Rep[String] = column[String]("email", O.Length(155,varying=true))
    /** Database column pass SqlType(varchar), Length(255,true) */
    val pass: Rep[String] = column[String]("pass", O.Length(255,varying=true))
    /** Database column creation_time SqlType(timestamp) */
    val creationTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("creation_time")

    /** Uniqueness Index over (email) (database name email_user_unique_index) */
    val index1 = index("email_user_unique_index", email, unique=true)
    /** Uniqueness Index over (nickname) (database name nickname_unique_index) */
    val index2 = index("nickname_unique_index", nickname, unique=true)
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Userses = new TableQuery(tag => new UsersRow(tag))
}
