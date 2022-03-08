package com.shoparty.android.ui.main.myaccount.myprofileupdate

data class MyProfileUpdateResponse(
    val message: String,
    val response_code: Int,
    val result: User
)
{
    data class User(
        val email: String,
        val gender: String,
        val image: String,
        val mobile: String,
        val name: String,
        val street_no: String,
        val building_no: String,


    )
}