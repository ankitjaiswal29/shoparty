package com.shoparty.android.ui.main.deals

import com.shoparty.android.network.RetrofitBuilder

/**
 * Created by Amit Gupta on 16-03-2022.
 */
class DealsRepository {
    suspend fun dealsApi(request:DealsRequestModel) =
        RetrofitBuilder.apiService?.getDeals(request)

}