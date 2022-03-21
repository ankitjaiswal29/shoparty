package com.shoparty.android.ui.search

import com.shoparty.android.network.RetrofitBuilder


class SearchRepository {

    suspend fun searchProduct(request: SearchRequestModel) =
        RetrofitBuilder.apiService?.searchProduct(request)
}