package com.shoparty.android.ui.address.addaddress.addaddress

data class GetCityResponse(
    val message: String,
    val response_code: Int,
    val result: List<Data>
)
{
    data class Data(
        val city_code: String,
        val city_id: Int,
        val city_name: String
    )
}