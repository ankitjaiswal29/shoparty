package com.shoparty.android.ui.main.myaccount.myprofileupdate

data class UpdateProfileRequestModel(
    val name: String = "",
    val email: String? = "",
    val mobile: String = "",
    val dob: String = "",
    val gender: String = "",
    val device_type: String = "",
    val device_token: String = ""
)