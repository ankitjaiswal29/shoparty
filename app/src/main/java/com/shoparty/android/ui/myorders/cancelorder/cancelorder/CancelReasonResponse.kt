package com.shoparty.android.ui.myorders.cancelorder.cancelorder

data class CancelReasonResponse(
    val message: String,
    val response_code: Int,
    val result: List<Result>
)
{
    data class Result(
        val ar_reason: String,
        val created_at: String,
        val deleted_at: Any,
        val id: Int,
        val reason: String,
        val status: Int,
        val updated_at: String
    )
}