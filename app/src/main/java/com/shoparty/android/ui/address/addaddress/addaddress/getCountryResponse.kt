package com.shoparty.android.ui.address.addaddress.addaddress

data class getCountryResponse(
    val message: String,
    val response_code: Int,
    val result: List<Data>
)
{
    data class Data(
        val country_code: String,
        val country_id: Int,
        val country_name: String,
        val phone_code: Int
    )
}