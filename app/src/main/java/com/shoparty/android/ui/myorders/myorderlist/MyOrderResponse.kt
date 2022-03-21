package com.shoparty.android.ui.myorders.myorderlist

data class MyOrderResponse(
    val message: String,
    val response_code: Int,
    val result: List<OrderHistory>
){
    data class OrderHistory(
        val order_date: String,
        val order_id: Int,
        val order_image: String,
        val order_name: String,
        val order_title: String
    )
}