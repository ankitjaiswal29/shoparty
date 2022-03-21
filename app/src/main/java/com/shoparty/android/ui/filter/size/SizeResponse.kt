package com.shoparty.android.ui.filter.size

data class SizeResponse(
    val message: String,
    val response_code: Int,
    val result: List<String>
)