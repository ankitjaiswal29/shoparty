package com.shoparty.android.ui.filter

import com.shoparty.android.network.RetrofitBuilder
import com.shoparty.android.ui.filter.color.ColorsRequestModel


class FilterRepository {
    suspend fun getColorsApi(request: ColorsRequestModel) =
        RetrofitBuilder.apiService?.colorsList(request)

    suspend fun getSizeApi() =
        RetrofitBuilder.apiService?.getListSize()

    suspend fun getGenderApi() =
        RetrofitBuilder.apiService?. getGenderList()

}