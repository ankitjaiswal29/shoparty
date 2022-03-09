package com.shoparty.android.ui.address.addaddress.addaddress

data class UpdateAddressResponse(
    val message: String,
    val response_code: Int,
    val result: Data
)
{
    data class Data(
        val address_id: Int,
        val building_no: String,
        val city: String,
        val country_id: String,
        val first_name: String,
        val last_name: String,
        val mobile_no: String,
        val street_no: String,
        val updated_at: String
    )
}