package com.shoparty.android.ui.main.home

import com.shoparty.android.network.RetrofitBuilder

class HomeRepository {
    suspend fun getDashboardData(requestModel: HomeRequestModel) = RetrofitBuilder.apiService?.getHomeData(requestModel)

}