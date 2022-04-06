package com.shoparty.android.ui.filter.color

data class ColorsResponse(
    val message: String,
    val response_code: Int,
    val result: List<Colors>
){
    data class Colors(
        val color_code: String,
        val color_name: String,
        val id: String
    )
}