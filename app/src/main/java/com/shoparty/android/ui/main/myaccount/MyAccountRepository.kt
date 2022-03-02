package com.shoparty.android.ui.main.myaccount

import com.shoparty.android.network.RetrofitBuilder
import com.shoparty.android.ui.main.myaccount.myprofileupdate.UpdateProfileRequestModel
import okhttp3.RequestBody


class MyAccountRepository {

    suspend fun logoutapi() =
        RetrofitBuilder.apiService?.logoutAsync()

    suspend fun getprofileapi() =
        RetrofitBuilder.apiService?.getProfileAsync()

    suspend fun updateprofileapi(requestBody: RequestBody) =
        RetrofitBuilder.apiService?.updateprofileAsync(requestBody)

}