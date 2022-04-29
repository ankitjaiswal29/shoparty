package com.shoparty.android.ui.notification

data class NotificationListResponse(
    val message: String,
    val response_code: Int,
    val result: List<Result>
)
{
    data class Result(
        val created_at: String,
        val description: String,
        val notification_id: Int,
        val title: String,
        val user_id: Int
    )
}