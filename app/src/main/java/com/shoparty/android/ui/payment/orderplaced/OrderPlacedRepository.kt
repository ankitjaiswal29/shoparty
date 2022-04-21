package com.shoparty.android.ui.payment.orderplaced

import com.shoparty.android.network.RetrofitBuilder


class OrderPlacedRepository {
    suspend fun orderplacedapi(orderPlacedRequestModel: OrderPlacedRequestModel) =
        RetrofitBuilder.apiService?.orderPlacedAsync(orderPlacedRequestModel)
}