package com.shoparty.android.ui.search

import com.shoparty.android.common_modal.Product

data class SearchResponseModel(
    val message: String,
    val response_code: Int,
    val home_result: ArrayList<Product>
)


