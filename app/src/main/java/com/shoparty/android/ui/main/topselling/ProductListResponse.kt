package com.shoparty.android.ui.main.topselling

data class ProductListResponse(
    val message: String,
    val response_code: Int,
    val result: List<ProductList>
){
    data class ProductList(
        val cost_price: String,
        val fav_status: String,
        val product_descripion: Any,
        val product_id: String,
        val product_image: String,
        val product_name: String,
        val sale_price: String
    )
}