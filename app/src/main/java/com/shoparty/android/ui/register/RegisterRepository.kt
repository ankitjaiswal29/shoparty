package com.shoparty.android.ui.register

import com.shoparty.android.network.RetrofitBuilder


class RegisterRepository {
    suspend fun postSignUp(request:RegisterRequestModel) =
        RetrofitBuilder.apiService?.registerAccountAsync(request)



}