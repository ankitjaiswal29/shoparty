package com.shoparty.android.ui.filter

import com.shoparty.android.network.RetrofitBuilder
import com.shoparty.android.ui.address.addaddress.addaddress.AddAddressRequestModel
import com.shoparty.android.ui.address.addaddress.addaddress.GetCityRequestModel
import com.shoparty.android.ui.address.addaddress.addaddress.UpdateAddressRequestModel
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressRequestModel


class ColorsRepository {
    suspend fun getColorsApi(request: ColorsRequestModel) =
        RetrofitBuilder.apiService?.colorsList(request)

    suspend fun getSizeApi() =
        RetrofitBuilder.apiService?.getListSize()

}