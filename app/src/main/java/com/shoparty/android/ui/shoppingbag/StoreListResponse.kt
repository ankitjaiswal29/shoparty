package com.shoparty.android.ui.shoppingbag

data class StoreListResponse(
    val message: String,
    val response_code: Int,
    val result: List<Result>
)
{
    data class Result(
        val store_name: String,
        val id: String
    )
}