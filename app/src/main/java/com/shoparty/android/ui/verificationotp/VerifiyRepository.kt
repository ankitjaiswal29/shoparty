package com.shoparty.android.ui.verificationotp

import com.shoparty.android.network.RetrofitBuilder


class VerifiyRepository {
    suspend fun verifiyapi(request: VerifiyRequestModel) =
        RetrofitBuilder.apiService?.verifiyAsync(request)

    suspend fun resendapi(request: ResendRequestModel) =
        RetrofitBuilder.apiService?.resendAsync(request)



}