package com.shoparty.android.ui.contactus

import com.shoparty.android.network.RetrofitBuilder


class ContactUsRepository {
    suspend fun contactusapi() =
        RetrofitBuilder.apiService?.getContactUsAsync()
}