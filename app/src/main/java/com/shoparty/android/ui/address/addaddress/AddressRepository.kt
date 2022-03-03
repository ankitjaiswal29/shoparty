package com.shoparty.android.ui.address.addaddress

import com.shoparty.android.network.RetrofitBuilder
import com.shoparty.android.ui.address.addaddress.addaddress.AddAddressRequestModel
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


}