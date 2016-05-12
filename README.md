# simpleapp
Application Product Shopping Cart Management API with Liftweb and PostgeSQL

# DEMO
http://api.degalab.com/v1

# List API
* USER
 * [GET] /me/cart  
   Get List cart from current user. Require authentication  
   Parameters
     * `offset` - _required_ (starting offset)
     * `limit` - _required_ (ends limit)
 * [DELETE] /me/cart/{CART-ID}  
   Delete cart from user. Require authentication
* PRODUCT
 * [GET] /product  
   Get List Product  
   Parameters
     * `offset` - _required_ (starting offset)
     * `limit` - _required_ (ends limit)
 * [GET] /product/{PRODUCT-ID}  
   Get Detail Product
 * [POST] /product  
   Insert new product. Require Authentication  
   Parameters
     * `name` - _required_ (product name)
     * `description` - _required_ (product description)
     * `stock` - _required_ (total product)
     * `price` - _required_ (price per product)
     * `size` - _optional_ default `empty` (product size. ex: XL/ L/ M)
     * `color` - _optional_ default `empty` (product color)
 * [DELETE] /product/{PRODUCT-ID}  
   Delete Product by ID. Require Authentication
 * [POST] /product/{PRODUCT-ID}/add-to-cart  
   Add Product to cart. Require Authentication  
   Parameters
     * `total` - _optional_ default 1 (Total Product)
* COUPON
 * [GET] /coupon  
   Get List coupon  
   Parameters
     * `offset` - _required_ (starting offset)
     * `limit` - _required_ (ends limit)
 * [GET] /coupon/{COUPON-ID}  
   Get detail coupon
 * [POST] /coupon  
   Insert new coupon. Require Authentication  
   Parameters
     * `coupon_code` - _required_ (coupon code. its unique)
     * `description` - _required_ (coupon description)
     * `price` - _required_ (discount price for coupon)
     * `is_valid` - _optional_ default 1 (coupon validation. 0: Invalid, 1: Valid)
 * [DELETE] /coupon/{COUPON-ID}  
   Delete coupon code by Id. Require Authentication
* CART
 * [POST] /cart/{CART-ID}/add-coupon  
   Add Coupon to cart. Require Authentication  
   Parameters
     * `coupon_code` - _required_ (coupon code)
 * [POST] /cart/{CART-ID}/remove-item/{CART-ITEM-ID}  
   Remove Item in cart. Require Authentication


### Authorization
For authorization, please use this dummy access_token `O7qcOGReTMgXRt_UZGw8gJilRKKvpDzt9639265022Z5Wa7TH8HzgHnq.BKb1_yGSbxYT-hakm9639265023owJTcgm-OE5m-Z6ZTMR_IZ7ATKhEQZr19639265024`
