# simpleapp
Application Product Shopping Cart Management API with Liftweb and PostgeSQL

# DEMO
http://api.degalab.com/v1

# List API
* USER
 * [GET] /me/cart - Authorization
 * [DELETE] /me/cart/{CART-ID} - Authorization
* PRODUCT
 * [GET] /product
 * [GET] /product/{PRODUCT-ID}
 * [POST] /product - Authorization
 * [DELETE] /product/{PRODUCT-ID}
 * [POST] /product/{PRODUCT-ID}/add-to-cart - Authorization
* COUPON
 * [GET] /coupon
 * [GET] /coupon/{COUPON-ID}
 * [POST] /coupon - Authorization
 * [DELETE] /coupon/{COUPON-ID} - Authorization
* CART
 * [POST] /cart/{CART-ID}/add-coupon - Authorization
 * [POST] /cart/{CART-ID}/remove-item/{CART-ITEM-ID} - Authorization


### Authorization
For authorization, please use this dummy access_token `O7qcOGReTMgXRt_UZGw8gJilRKKvpDzt9639265022Z5Wa7TH8HzgHnq.BKb1_yGSbxYT-hakm9639265023owJTcgm-OE5m-Z6ZTMR_IZ7ATKhEQZr19639265024`