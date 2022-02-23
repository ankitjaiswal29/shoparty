package com.shoparty.android.ui.verificationotp

data class ResendOtpResponse(
    val message: String,
    val otp: Int,
    val response_code: Int,
    val status: Int
)