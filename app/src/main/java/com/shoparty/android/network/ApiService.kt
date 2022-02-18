package com.shoparty.android.network


import com.shoparty.android.ui.register.RegisterRequestModel
import com.shoparty.android.ui.register.RegisterResponseModel
import com.shoparty.android.utils.apiutils.ApiResponse
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.POST
interface ApiService {
    @POST("register")                //
    suspend fun registerAccountAsync(
        @Body registerRequestModel: RegisterRequestModel
    ): Response<RegisterResponseModel>
}