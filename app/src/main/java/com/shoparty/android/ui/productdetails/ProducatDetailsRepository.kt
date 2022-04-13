package com.shoparty.android.ui.productdetails

import com.shoparty.android.network.RetrofitBuilder


class ProducatDetailsRepository {
    suspend fun producatdetailsapi(request:ProducatDetailsRequestModel) =
        RetrofitBuilder.apiService?.producatdetailsAsync(request)


    suspend fun addtobagapi(request:AddItemToBagRequestModel) =
        RetrofitBuilder.apiService?.addtobagAsync(request)
}