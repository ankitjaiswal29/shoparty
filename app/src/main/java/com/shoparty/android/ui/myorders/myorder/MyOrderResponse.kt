package com.shoparty.android.ui.myorders.myorder

data class MyOrderResponse(
    val message: String,
    val response_code: Int,
    val result: List<Data>
){
    data class Data(
        val order_date: String,
        val order_id: Int,
        val order_image: String,
        val order_name: String,
        val order_title: String
    )
}