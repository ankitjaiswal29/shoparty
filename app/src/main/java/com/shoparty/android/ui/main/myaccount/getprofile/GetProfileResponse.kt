package com.shoparty.android.ui.main.myaccount.getprofile

data class GetProfileResponse(
    val message: String,
    val response_code: Int,
    val result: User
)
{
    data class User(
        val dob: String,
        val email: String,
        val gender: String,
        val image: String,
        val mobile: Long,
        val name: String,
        val role: Int,
        val user_id: Int,
    )


}