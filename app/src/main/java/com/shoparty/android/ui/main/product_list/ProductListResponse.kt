package com.shoparty.android.ui.main.product_list

import com.shoparty.android.common_modal.Product

data class ProductListResponse(
    val message: String,
    val response_code: Int,
    val result: List<Product>
)

