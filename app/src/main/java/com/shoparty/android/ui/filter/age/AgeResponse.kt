package com.shoparty.android.ui.filter.age

data class AgeResponse(
    val message: String,
    val response_code: Int,
    val result: List<Result>
)
{
    data class Result(
        val age_from: String,
        val age_to: String
    )
}