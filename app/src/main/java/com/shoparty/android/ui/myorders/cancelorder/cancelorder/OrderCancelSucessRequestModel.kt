package com.shoparty.android.ui.myorders.cancelorder.cancelorder

data class OrderCancelSucessRequestModel(
    val cancellation_reason: String = "",
    val order_id: String = "",
)
