package com.shoparty.android.ui.main.topselling

data class ProductListRequestModel(
    val  filter_id:String="",
    val  type:String="",
    val  language_id: String = "",
    val  user_id: String = "",
)
