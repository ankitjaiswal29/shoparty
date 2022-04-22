package com.shoparty.android.ui.productdetails

import com.shoparty.android.network.RetrofitBuilder
import okhttp3.RequestBody


class ProducatDetailsRepository {
    suspend fun producatdetailsapi(request:ProducatDetailsRequestModel) =
        RetrofitBuilder.apiService?.producatdetailsAsync(request)


    suspend fun addtobagapi(requestBody: RequestBody) =
        RetrofitBuilder.apiService?.addtobagAsync(requestBody)
}