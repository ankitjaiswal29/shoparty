package com.shoparty.android.ui.payment.orderplaced

data class OrderPlacedRequestModel(
    val language_id: String? = "",
    val shopping_id: ArrayList<String>?,
    val payment_status: String? = "",
    val payment_type: String? = "",
    val transaction_id: String? = "",
    val order_type: String? = "",
    val promocode_id: String? = "",
    val amount: String? = "",
    val discount: String? = "",
    val tax: String? = "",
    val total_amount: String? = "",
    val address_id: String? = "",
    val is_deliverable: String? = "",
    val store_id: String? = "",
)