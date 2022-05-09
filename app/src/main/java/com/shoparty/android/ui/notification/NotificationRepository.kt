package com.shoparty.android.ui.notification

import com.shoparty.android.network.RetrofitBuilder


class NotificationRepository {
    suspend fun notificationList(request:NotificationListRequestModel) =
       RetrofitBuilder.apiService?.notificationListAsync(request)
}