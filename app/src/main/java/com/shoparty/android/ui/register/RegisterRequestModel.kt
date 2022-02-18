package com.shoparty.android.ui.register

data class RegisterRequestModel(
    val contact_number: String = "",
    val deviceToken: String? = "",
    val deviceType: String = "",
    val dob: String = "",
    val email: String = "",
    val gender: String = "",
    val lat: String = "0",
    val long: String = "0",
    val name: String = "",
    val password: String = "",
    val username: String = ""
)