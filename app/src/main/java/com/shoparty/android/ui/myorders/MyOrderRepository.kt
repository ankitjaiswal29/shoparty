package com.shoparty.android.ui.myorders

import com.shoparty.android.network.RetrofitBuilder
import com.shoparty.android.ui.myorders.cancelorder.cancelorder.OrderCancelSucessRequestModel
import com.shoparty.android.ui.myorders.myorderlist.MyOrderRequestModel
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsRequestModel


class MyOrderRepository {
    suspend fun getmyorderapi(request: MyOrderRequestModel) =
        RetrofitBuilder.apiService?.getMyOrderDataAsync(request)


    suspend fun getorderdetailsapi(request: OrderDetailsRequestModel) =
        RetrofitBuilder.apiService?.orderdetails(request)


    suspend fun cancelreasonapi(request: MyOrderRequestModel) =
        RetrofitBuilder.apiService?.cancelReasonAsync(request)


    suspend fun ordercancelapi(request: OrderCancelSucessRequestModel) =
        RetrofitBuilder.apiService?.orderCancelAsync(request)
}