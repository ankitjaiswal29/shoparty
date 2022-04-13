package com.shoparty.android.ui.productdetails

data class AddItemToBagRequestModel(
    val product_id: String = "",
    val product_detail_id: String = "",
    val product_size_id: String? = "",
    val product_color_id: String? = "",
    val quantity: Int? = 0,
    val price: String = "",
)

