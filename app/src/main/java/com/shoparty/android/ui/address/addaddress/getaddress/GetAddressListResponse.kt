package com.shoparty.android.ui.address.addaddress.getaddress

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class GetAddressListResponse(
    val message: String,
    val response_code: Int,
    val result: List<AddressData>
): Parcelable
{
    @Parcelize
    data class AddressData(
        val address_id: Int,
        val building_no: String?="",
        val city_name: String,
        val country_id: Int,
        val created_at: String,
        val first_name: String,
        val last_name: String,
        val mobile: String,
        val street_no: String?="",
        val country_name: String,
        val country_code: String,
        val city_id: String,
    ):Parcelable
}