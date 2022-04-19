package com.shoparty.android.ui.main.product_list

data class TopSellingRequestModel(
    val language_id: String = "",
    val type: String = "",
    val offset: String = "",
    val limit: String = "",
    val user_id: String = "",
    val filter_applied: String = "",
    val filter: ProductListRequestModel.Filter,
    val sort_applied: Int=0,
    val sort_type: Int=0
)