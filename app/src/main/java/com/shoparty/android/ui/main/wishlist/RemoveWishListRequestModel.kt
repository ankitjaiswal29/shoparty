package com.shoparty.android.ui.main.wishlist

data class RemoveWishListRequestModel(
    val product_id: String = "",
    val type: Int = 0,
    val product_detail_id: Int = 0,
    val product_size_id:String,
    val product_color_id:String

)