package com.shoparty.android.ui.login

data class LoginRequestModel(
    val mobile: String = "",
    val device_token: String? = "",
    val device_type: String = "",
    val type: String = ""
)