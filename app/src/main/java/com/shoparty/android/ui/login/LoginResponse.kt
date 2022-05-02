package com.shoparty.android.ui.login

data class LoginResponse(
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
        val is_active: Int,
        val mobile: String,
        val name: String,
        val otp: Int,
        val token: String,
        val language_id: Int,
        val user_id: Int,
        val dob: String,
        val completely_registered: String,
    )
}