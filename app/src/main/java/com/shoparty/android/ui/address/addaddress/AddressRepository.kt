package com.shoparty.android.ui.address.addaddress

import com.shoparty.android.network.RetrofitBuilder
import com.shoparty.android.ui.address.addaddress.addaddress.AddAddressRequestModel


class AddressRepository {
    suspend fun addaddressapi(request:AddAddressRequestModel) =
        RetrofitBuilder.apiService?.addAddressAsync(request)

    suspend fun getcountryapi() =
        RetrofitBuilder.apiService?.getcountryAsync()



}