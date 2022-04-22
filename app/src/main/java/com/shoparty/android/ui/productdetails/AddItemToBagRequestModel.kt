package com.shoparty.android.ui.productdetails

import java.io.File

data class AddItemToBagRequestModel(
    val product_id: Int,
    val product_detail_id: String = "",
    val product_size_id: String? = "",
    val product_color_id: String? = "",
    val quantity: Int? = 0,
    val price: String = "",
    val image: String = "",
    val is_customizable: String = "",

)

