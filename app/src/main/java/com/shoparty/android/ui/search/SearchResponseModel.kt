package com.shoparty.android.ui.search

import com.shoparty.android.common_modal.Category
import com.shoparty.android.common_modal.Product

data class SearchResponseModel(
    val categories: List<Category>,
    val message: String,
    val products: List<Product>,
    val response_code: Int
)

