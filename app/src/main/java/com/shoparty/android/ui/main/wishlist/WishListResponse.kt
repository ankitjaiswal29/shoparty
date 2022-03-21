package com.shoparty.android.ui.main.wishlist

data class WishListResponse(
    val message: String,
    val response_code: Int,
    val result: List<Data>
){
    data class Data(
        val cart_status: String,
        val cost_price: String,
        val discount: String,
        val id: String,
        val product_id: String,
        val product_image: String,
        val product_name: String,
        val sale_price: String
    )
}