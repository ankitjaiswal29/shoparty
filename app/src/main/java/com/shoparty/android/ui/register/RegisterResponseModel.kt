package com.shoparty.android.ui.register

data class RegisterResponseModel(
    val message: String,
    val response_code: Int,
    val result: User
)
{
    data class User(
        val created_at: String,
        val device_token: String,
        val device_type: String,
        val email: String,
        val gender: String,
        val image: String,
        val mobile: String,
        val name: String,
        val otp: Int,
        val token: String,
        val user_id: Int
    )
}

