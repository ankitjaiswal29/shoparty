package com.shoparty.android.ui.myorders.orderdetails

data class OrderDetailsResponse(
    val message: String,
    val response_code: Int,
    val result: OrderList
)
{
    data class OrderList(
        val action_status: String,
        val order_id: Any,
        val order_number: String,
        val order_title: String,
        val product_response: List<ProductResponse>,
        val tax: String,
        val total_amount: String,
        val total_qnty: String)

    data class ProductResponse(
        val age_from: String,
        val age_to: String,
        val ar_color_name: String,
        val ar_description: String,
        val ar_name: String,
        val brand_id: Int,
        val color_id: String,
        val color_name: String,
        val cost_price: String,
        val created_at: String,
        val deleted_at: Any,
        val delivery_time: String,
        val discount: String,
        val en_description: String,
        val en_name: String,
        val gender: String,
        val id: Int,
        val image: String,
        val is_customizable: Int,
        val is_deliverable: Int,
        val product_age_id: Int,
        val product_color_id: String,
        val product_descripion: String,
        val product_detail_id: Int,
        val product_id: Int,
        val product_image_id: Int,
        val product_name: String,
        val product_price: String,
        val product_qnty: String,
        val product_quantity_id: Int,
        val product_size_id: Int,
        val quantity: String,
        val sale_price: String,
        val season_id: Any,
        val size: String,
        val status: Int,
        val store_id: Any,
        val tax: String,
        val tax_type: Int,
        val theme_id: Any,
        val updated_at: String
    )



}