package com.shoparty.android.ui.login

import com.shoparty.android.network.RetrofitBuilder


class LoginRepository {
    suspend fun loginapi(request:LoginRequestModel) =
        RetrofitBuilder.apiService?.loginAsync(request)
}