package com.shoparty.android.ui.verificationotp

data class VerifiyRequestModel(
    val otp: String = "",
    val user_id: String? = "",
)