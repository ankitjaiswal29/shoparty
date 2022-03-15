package com.shoparty.android.ui.main.topselling

import com.shoparty.android.network.RetrofitBuilder
import com.shoparty.android.ui.address.addaddress.addaddress.AddAddressRequestModel
import com.shoparty.android.ui.address.addaddress.addaddress.GetCityRequestModel
import com.shoparty.android.ui.address.addaddress.addaddress.UpdateAddressRequestModel
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressRequestModel


class ProductListRepository {
    suspend fun getProductListApi(request: ProductListRequestModel) =
        RetrofitBuilder.apiService?.getProductList(request)

}