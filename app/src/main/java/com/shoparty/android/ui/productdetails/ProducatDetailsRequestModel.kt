package com.shoparty.android.ui.productdetails

data class ProducatDetailsRequestModel(
    val language_id: String = "",
    val product_detail_id: String = "",
    val product_id: String? = "",
    val product_size_id: String? = "",
    val product_color_id: String? = "",
)