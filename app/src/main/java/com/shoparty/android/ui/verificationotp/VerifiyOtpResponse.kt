package com.shoparty.android.ui.verificationotp

data class VerifiyOtpResponse(
    val message: String,
    val response_code: Int,
    val result: User,
    val token: String
)
{
    data class User(
        val `data`: Data
    )

    data class Data(
        val email: String,
        val image: String,
        val mobile: Long,
        val name: String,
        val role: Int,
        val user_id: Int
    )


}