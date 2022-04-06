package com.shoparty.android.ui.filter.gender

data class GenderResponse(
    val message: String,
    val response_code: Int,
    val result: List<String>
)