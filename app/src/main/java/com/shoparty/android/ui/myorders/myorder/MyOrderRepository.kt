package com.shoparty.android.ui.myorders.myorder

import com.shoparty.android.network.RetrofitBuilder
import com.shoparty.android.ui.address.addaddress.addaddress.AddAddressRequestModel
import com.shoparty.android.ui.address.addaddress.addaddress.GetCityRequestModel
import com.shoparty.android.ui.address.addaddress.addaddress.UpdateAddressRequestModel
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressRequestModel


class MyOrderRepository {
    suspend fun getmyorderapi(request: MyOrderRequestModel) =
        RetrofitBuilder.apiService?.getMyOrderDataAsync(request)

}