package com.shoparty.android.ui.main.deals

import com.google.gson.JsonObject
import com.shoparty.android.ui.main.product_list.ProductListRequestModel


data class DealsRequestModel(
    val language_id: String = "",
    val offset: String = "",
    val limit: String = "",
    val user_id: String = "",
    val filter_applied: String = "",
    val filter:ProductListRequestModel.Filter,
    val sort_applied: Int=0,
    val sort_type: Int=0,
)