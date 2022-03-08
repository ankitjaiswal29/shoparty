package com.shoparty.android.ui.address.addaddress.addaddress

data class AddAddressRequestModel(
    val first_name: String = "",
    val last_name: String? = "",
    val country_id: String? = "",
    val city_id: String? = "",
    val street_no: String? = "",
    val building_no: String? = "",
    val mobile: String? = "",
)