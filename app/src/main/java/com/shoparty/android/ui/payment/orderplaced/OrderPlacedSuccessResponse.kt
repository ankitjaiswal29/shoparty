package com.shoparty.android.ui.payment.orderplaced

data class OrderPlacedSuccessResponse(
    val message: String,
    val response_code: Int,
    val result: OrderSucessPlaced
)
{
    data class OrderSucessPlaced(
        val action_date: String,
        val action_status: Int,
        val address_id: String,
        val amount_to_paid: String,
        val discount: String,
        val order_detail: List<OrderDetail>,
        val order_id: Int,
        val order_number: String,
        val order_status: OrderStatus,
        val payment_status: String,
        val payment_type: String,
        val promocode_id: String,
        val status: Int,
        val tax: String,
        val total_amount: String,
        val transaction_id: String,
        val user_id: Int
    )

    data class OrderDetail(
        val customized_id: Any,
        val order_detail_id: Int,
        val order_id: Int,
        val price: String,
        val product_color_id: Int,
        val product_detail_id: Int,
        val product_id: Int,
        val product_size_id: Int,
        val quantity: String
    )

    data class OrderStatus(
        val order_id: Int,
        val order_status: Int,
        val order_status_id: Int,
        val status_date: String
    )

}