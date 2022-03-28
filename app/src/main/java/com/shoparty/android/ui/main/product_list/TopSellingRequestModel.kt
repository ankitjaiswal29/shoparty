package com.shoparty.android.ui.main.product_list

data class TopSellingRequestModel(
    val language_id: String = "",
    val type: String = "",
    val offset: String = "",
    val limit: String = "",
    val user_id: String
)