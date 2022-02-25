package com.shoparty.android.ui.main.myaccount

import com.shoparty.android.network.RetrofitBuilder


class MyAccountRepository {

    suspend fun logoutapi() =
        RetrofitBuilder.apiService?.logoutAsync()

}