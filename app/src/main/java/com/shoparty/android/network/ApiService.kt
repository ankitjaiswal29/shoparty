package com.shoparty.android.network


import com.shoparty.android.ui.address.addaddress.addaddress.*
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressRequestModel
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressResponse
import com.shoparty.android.ui.address.addaddress.getaddress.GetAddressListResponse
import com.shoparty.android.ui.login.LoginRequestModel
import com.shoparty.android.ui.login.LoginResponse
import com.shoparty.android.ui.main.myaccount.logout.LogoutResponse
import com.shoparty.android.ui.main.myaccount.getprofile.getProfileResponse
import com.shoparty.android.ui.main.myaccount.myprofileupdate.MyProfileUpdateResponse
import com.shoparty.android.ui.register.RegisterRequestModel
import com.shoparty.android.ui.register.RegisterResponseModel
import com.shoparty.android.ui.verificationotp.ResendOtpResponse
import com.shoparty.android.ui.verificationotp.ResendRequestModel
import com.shoparty.android.ui.verificationotp.VerifiyOtpResponse
import com.shoparty.android.ui.verificationotp.VerifiyRequestModel
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.POST
interface ApiService {

    //hemraj dangi api

    @POST("signup")                //
    suspend fun registerAccountAsync(
        @Body registerRequestModel: RegisterRequestModel
    ): Response<RegisterResponseModel>


    @POST("login")                //
    suspend fun loginAsync(
        @Body loginRequestModel: LoginRequestModel
    ): Response<LoginResponse>

    @POST("verify-otp")                //
    suspend fun verifiyAsync(
        @Body verifiyRequestModel: VerifiyRequestModel
    ): Response<VerifiyOtpResponse>


    @POST("resend-otp")                //
    suspend fun resendAsync(
        @Body resendRequestModel: ResendRequestModel
    ): Response<ResendOtpResponse>



    @GET("logout")     //
    suspend fun logoutAsync():
            Response<LogoutResponse>

    @GET("profile-details")     //
    suspend fun getProfileAsync():
            Response<getProfileResponse>

    @POST("update-profile")              //
    suspend fun updateprofileAsync(
        @Body requestBody: RequestBody
    ): Response<MyProfileUpdateResponse>


    @POST("add-address")                //
    suspend fun addAddressAsync(
        @Body addAddressRequestModel: AddAddressRequestModel
    ): Response<AddAddressResponse>


    @GET("list-countries")     //
    suspend fun getcountryAsync():
            Response<GetCountryResponse>


    @GET("list-addresses")     //
    suspend fun getaddressAsync():
            Response<GetAddressListResponse>

    @POST("delete-address")                //
    suspend fun deleteAddressAsync(
        @Body deleteAddressRequestModel: DeleteAddressRequestModel
    ): Response<DeleteAddressResponse>


    @POST("list-cities")     //
    suspend fun getcityAsync(
        @Body requestModel: GetCityRequestModel):
            Response<GetCityResponse>


}