package com.shoparty.android.ui.login

data class LoginRequestModel(
    val mobile: String = "",
    val device_token: String? = "",
    val device_type: String = "",
    var type: String = "",
    var country_code: String = "",
)