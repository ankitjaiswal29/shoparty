package com.shoparty.android.ui.address.addaddress.getaddress

data class GetAddressListResponse(
    val message: String,
    val response_code: Int,
    val result: List<Data>
)
{
    data class Data(
        val address_id: Int,
        val building_no: String,
        val city: String,
        val country_id: Int,
        val created_at: String,
        val first_name: String,
        val last_name: String,
        val mobile: String,
        val street_no: String
    )
}