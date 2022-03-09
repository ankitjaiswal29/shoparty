package com.shoparty.android.ui.address.addaddress

import com.shoparty.android.network.RetrofitBuilder
import com.shoparty.android.ui.address.addaddress.addaddress.AddAddressRequestModel
import com.shoparty.android.ui.address.addaddress.addaddress.GetCityRequestModel
import com.shoparty.android.ui.address.addaddress.addaddress.UpdateAddressRequestModel
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressRequestModel


class AddressRepository {
    suspend fun addaddressapi(request:AddAddressRequestModel) =
        RetrofitBuilder.apiService?.addAddressAsync(request)

    suspend fun getcountryapi() =
        RetrofitBuilder.apiService?.getcountryAsync()

    suspend fun getaddressapi() =
        RetrofitBuilder.apiService?.getaddressAsync()

    suspend fun deleteaddressapi(request: DeleteAddressRequestModel) =
        RetrofitBuilder.apiService?.deleteAddressAsync(request)


    suspend fun getcityapi(request: GetCityRequestModel) =
        RetrofitBuilder.apiService?.getcityAsync(request)

    suspend fun updateaddressapi(request:UpdateAddressRequestModel) =
        RetrofitBuilder.apiService?.updateAddressAsync(request)


}