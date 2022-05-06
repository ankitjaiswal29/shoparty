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
        val country_code: String,
        val name: String,
        val role: Int,
        val user_id: Int,
        val city_id:String,
        val street_no:String,
        val building_no:String
    )


}