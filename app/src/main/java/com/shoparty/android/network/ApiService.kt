package com.shoparty.android.network

import com.shoparty.android.ui.address.addaddress.addaddress.*
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressRequestModel
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressResponse
import com.shoparty.android.ui.address.addaddress.getaddress.GetAddressListResponse
import com.shoparty.android.ui.contactus.ContactUsResponse
import com.shoparty.android.ui.login.LoginRequestModel
import com.shoparty.android.ui.login.LoginResponse
import com.shoparty.android.ui.main.categories.CategoryRequestModel
import com.shoparty.android.ui.main.categories.CategoryResponse
import com.shoparty.android.ui.main.home.HomeRequestModel
import com.shoparty.android.ui.main.home.HomeResponse
import com.shoparty.android.ui.main.myaccount.getprofile.getProfileResponse
import com.shoparty.android.ui.main.myaccount.logout.LogoutResponse
import com.shoparty.android.ui.main.myaccount.myprofileupdate.MyProfileUpdateResponse
import com.shoparty.android.ui.main.wishlist.WishListRequestModel
import com.shoparty.android.ui.main.wishlist.WishListResponse
import com.shoparty.android.ui.myorders.myorder.MyOrderRequestModel
import com.shoparty.android.ui.myorders.myorder.MyOrderResponse
import com.shoparty.android.ui.register.RegisterRequestModel
import com.shoparty.android.ui.register.RegisterResponseModel
import com.shoparty.android.ui.verificationotp.ResendOtpResponse
import com.shoparty.android.ui.verificationotp.ResendRequestModel
import com.shoparty.android.ui.verificationotp.VerifiyOtpResponse
import com.shoparty.android.ui.verificationotp.VerifiyRequestModel
import com.shoparty.android.ui.vouchers.VoucherListResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("signup")
    suspend fun registerAccountAsync(
        @Body registerRequestModel: RegisterRequestModel
    ): Response<RegisterResponseModel>


    @POST("login")
    suspend fun loginAsync(
        @Body loginRequestModel: LoginRequestModel
    ): Response<LoginResponse>

    @POST("verify-otp")
    suspend fun verifiyAsync(
        @Body verifiyRequestModel: VerifiyRequestModel
    ): Response<VerifiyOtpResponse>

    @POST("resend-otp")
    suspend fun resendAsync(
        @Body resendRequestModel: ResendRequestModel
    ): Response<ResendOtpResponse>

    @GET("logout")
    suspend fun logoutAsync():
            Response<LogoutResponse>

    @GET("list-vouchers")
    suspend fun VoucherAsync():
            Response<VoucherListResponse>



    @GET("profile-details")
    suspend fun getProfileAsync():
            Response<getProfileResponse>


    @GET("contact_us")
    suspend fun getContactUsAsync():
            Response<ContactUsResponse>

    @POST("update-profile")
    suspend fun updateprofileAsync(
        @Body requestBody: RequestBody
    ): Response<MyProfileUpdateResponse>


    @POST("add-address")
    suspend fun addAddressAsync(
        @Body addAddressRequestModel: AddAddressRequestModel
    ): Response<AddAddressResponse>


    @POST("edit-address")                //
    suspend fun updateAddressAsync(
        @Body updateAddressRequestModel: UpdateAddressRequestModel
    ): Response<UpdateAddressResponse>


    @GET("list-countries")
    suspend fun getcountryAsync(): Response<GetCountryResponse>


    @GET("list-addresses")
    suspend fun getaddressAsync():
            Response<GetAddressListResponse>

    @POST("delete-address")
    suspend fun deleteAddressAsync(
        @Body deleteAddressRequestModel: DeleteAddressRequestModel
    ): Response<DeleteAddressResponse>


    @POST("list-cities")
    suspend fun getcityAsync(
        @Body requestModel: GetCityRequestModel
    ): Response<GetCityResponse>

    @POST("home-screen")
    suspend fun getHomeData(
        @Body requestModel: HomeRequestModel
    ): Response<HomeResponse>

    @POST("list-categories")
    suspend fun getCategory(
        @Body requestModel: CategoryRequestModel
    ): Response<CategoryResponse>

    @POST("list-orders")
    suspend fun getMyOrderDataAsync(
        @Body myOrderRequestModel: MyOrderRequestModel
    ): Response<MyOrderResponse>

    @POST("my-wishlist")
    suspend fun getWishlist(
        @Body wishListRequestModel: WishListRequestModel
    ): Response<WishListResponse>

}