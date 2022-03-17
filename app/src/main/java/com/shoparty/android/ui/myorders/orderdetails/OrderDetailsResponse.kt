package com.shoparty.android.ui.myorders.orderdetails

data class OrderDetailsResponse(
    val message: String,
    val response_code: Int,
    val result: OrderList
)
{
    data class OrderList(
        val action_status: String,
        val amount_to_paid: String,
        val order_id: String,
        val order_title: String,
        val product_response: List<ProductResponse>,
        val tax: String,
        val total_amount: String,
        val total_qnty: String
    )
    data class ProductResponse(
        val id: String,
        val product_id: String,
        val product_image: String,
        val product_name: String,
        val product_price: String,
        val product_qnty: String
    )
}