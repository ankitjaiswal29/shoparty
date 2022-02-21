package com.shoparty.android.network


import com.shoparty.android.ui.login.LoginRequestModel
import com.shoparty.android.ui.register.RegisterRequestModel
import com.shoparty.android.ui.register.RegisterResponseModel
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.POST
interface ApiService {
    @POST("signup")                //
    suspend fun registerAccountAsync(
        @Body registerRequestModel: RegisterRequestModel
    ): Response<RegisterResponseModel>


    @POST("login")                //
    suspend fun loginAsync(
        @Body loginRequestModel: LoginRequestModel
    ): Response<RegisterResponseModel>
}