package com.shoparty.android.interfaces

interface RecyclerViewFavouriteListener {
    fun favourite(
        position: Int,
        producat_id: String,
        type: String,
        product_detail_id: String,
        product_sizeId: String,
        product_colorId: String
    )
}
