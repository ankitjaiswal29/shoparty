package com.shoparty.android.network

import com.shoparty.android.ui.address.addaddress.addaddress.*
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressRequestModel
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressResponse
import com.shoparty.android.ui.address.addaddress.getaddress.GetAddressListResponse
import com.shoparty.android.ui.contactus.ContactUsResponse
import com.shoparty.android.ui.filter.age.AgeResponse
import com.shoparty.android.ui.filter.color.ColorsRequestModel
import com.shoparty.android.ui.filter.color.ColorsResponse
import com.shoparty.android.ui.filter.gender.GenderResponse
import com.shoparty.android.ui.filter.size.SizeResponse
import com.shoparty.android.ui.login.LoginRequestModel
import com.shoparty.android.ui.login.LoginResponse
import com.shoparty.android.ui.main.categories.CategoryRequestModel
import com.shoparty.android.ui.main.categories.CategoryResponse
import com.shoparty.android.ui.main.deals.DealsRequestModel
import com.shoparty.android.ui.main.drawer.drawer_main_category.DrawerResponse
import com.shoparty.android.ui.main.home.HomeRequestModel
import com.shoparty.android.ui.main.home.HomeResponse
import com.shoparty.android.ui.main.mainactivity.ChangeLanguageRequestModel
import com.shoparty.android.ui.main.mainactivity.ChangeLanguageResponse
import com.shoparty.android.ui.main.myaccount.getprofile.GetProfileResponse
import com.shoparty.android.ui.main.myaccount.logout.LogoutResponse
import com.shoparty.android.ui.main.myaccount.myprofileupdate.MyProfileUpdateResponse
import com.shoparty.android.ui.main.product_list.ProductListRequestModel
import com.shoparty.android.ui.main.product_list.ProductListResponse
import com.shoparty.android.ui.main.product_list.TopSellingRequestModel
import com.shoparty.android.ui.main.wishlist.RemoveWishListRequestModel
import com.shoparty.android.ui.main.wishlist.RemoveWishlistResponse
import com.shoparty.android.ui.main.wishlist.WishListRequestModel
import com.shoparty.android.ui.main.wishlist.WishListResponse
import com.shoparty.android.ui.myorders.cancelorder.cancelorder.CancelReasonResponse
import com.shoparty.android.ui.myorders.cancelorder.cancelorder.OrderCancelSucessRequestModel
import com.shoparty.android.ui.myorders.cancelorder.cancelorder.OrderCancelSuccessfully
import com.shoparty.android.ui.myorders.myorderlist.MyOrderRequestModel
import com.shoparty.android.ui.myorders.myorderlist.MyOrderResponse
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsRequestModel
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsResponse
import com.shoparty.android.ui.notification.NotificationListRequestModel
import com.shoparty.android.ui.notification.NotificationListResponse
import com.shoparty.android.ui.payment.orderplaced.OrderPlacedRequestModel
import com.shoparty.android.ui.payment.orderplaced.OrderPlacedSuccessResponse
import com.shoparty.android.ui.productdetails.AddItemBagResponse
import com.shoparty.android.ui.productdetails.AddItemToBagRequestModel
import com.shoparty.android.ui.productdetails.ProducatDetailsRequestModel
import com.shoparty.android.ui.productdetails.ProducatDetailsResponse
import com.shoparty.android.ui.register.RegisterRequestModel
import com.shoparty.android.ui.register.RegisterResponseModel
import com.shoparty.android.ui.search.SearchRequestModel
import com.shoparty.android.ui.search.SearchResponseModel
import com.shoparty.android.ui.shoppingbag.CartItemRemoveRequestModel
import com.shoparty.android.ui.shoppingbag.ShoppingBagRequestModel
import com.shoparty.android.ui.shoppingbag.ShoppingBagResponse
import com.shoparty.android.ui.shoppingbag.StoreListResponse
import com.shoparty.android.ui.verificationotp.ResendOtpResponse
import com.shoparty.android.ui.verificationotp.ResendRequestModel
import com.shoparty.android.ui.verificationotp.VerifiyOtpResponse
import com.shoparty.android.ui.verificationotp.VerifiyRequestModel
import com.shoparty.android.ui.vouchers.VoucherListResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
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
            Response<GetProfileResponse>

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

    @POST("edit-address")
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

    @GET("list-genders")
    suspend fun getGenderList(
    ): Response<GenderResponse>

    @GET("list-ages")
    suspend fun changeAge(
    ): Response<AgeResponse>

    @POST("list-orders")
    suspend fun getMyOrderDataAsync(
        @Body myOrderRequestModel: MyOrderRequestModel
    ): Response<MyOrderResponse>

    @POST("my-wishlist")
    suspend fun getWishlist(
        @Body wishListRequestModel: WishListRequestModel
    ): Response<WishListResponse>

    @POST("product-list")
    suspend fun getProductList(
        @Body productListRequestModel: ProductListRequestModel
    ): Response<ProductListResponse>

    @POST("arrival/and/top/list")
    suspend fun getTopProductList(
        @Body topSellingRequestModel: TopSellingRequestModel
    ): Response<ProductListResponse>

    @POST("add-wishlist")
    suspend fun addremoveWishlist(
        @Body removeWishListRequestModel: RemoveWishListRequestModel
    ): Response<RemoveWishlistResponse>

    @POST("list-colors")
    suspend fun colorsList(
        @Body colorsRequestModel: ColorsRequestModel
    ): Response<ColorsResponse>

    @POST("list-deals")
    suspend fun getDeals(
        @Body requestModel: DealsRequestModel
    ): Response<ProductListResponse>

    @POST("sidebar/product/categories")
    suspend fun getDrawer(
        @Body requestModel: CategoryRequestModel
    ): Response<DrawerResponse>

    @POST("order-details")
    suspend fun orderdetails(
        @Body orderDetailsRequestModel: OrderDetailsRequestModel
    ): Response<OrderDetailsResponse>

    @GET("list-sizes")
    suspend fun getListSize(
    ): Response<SizeResponse>

    @POST("home/screen/search")
    suspend fun searchProduct(
        @Body requestModel: SearchRequestModel
    ): Response<SearchResponseModel>

    @POST("product-details")
    suspend fun producatdetailsAsync(
        @Body producatDetailsRequestModel: ProducatDetailsRequestModel
    ): Response<ProducatDetailsResponse>


    @POST("add-order")
    suspend fun orderPlacedAsync(
        @Body orderPlacedRequestModel: OrderPlacedRequestModel
    ): Response<OrderPlacedSuccessResponse>

    @POST("add-to-bag")
    suspend fun addtobagAsync(
        @Body requestBody: RequestBody
    ): Response<AddItemBagResponse>

    @POST("shopping-list")
    suspend fun shoppingListAsync(
        @Body shoppingBagRequestModel: ShoppingBagRequestModel
    ): Response<ShoppingBagResponse>



    @POST("notifications")
    suspend fun notificationListAsync(
        @Body notificationListRequestModel: NotificationListRequestModel
    ): Response<NotificationListResponse>

    @POST("stores-list")
    suspend fun storeListAsync(
        @Body shoppingBagRequestModel: ShoppingBagRequestModel
    ): Response<StoreListResponse>

    @POST("cancellation-reasons")
    suspend fun cancelReasonAsync(
        @Body myOrderRequestModel: MyOrderRequestModel
    ): Response<CancelReasonResponse>

    @POST("cancel-order")
    suspend fun orderCancelAsync(
        @Body orderCancelSucessRequestModel: OrderCancelSucessRequestModel
    ): Response<OrderCancelSuccessfully>



    @POST("delete-cart-product")
    suspend fun deleteCartItemAsync(
        @Body cartItemRemoveRequestModel: CartItemRemoveRequestModel
    ): Response<ShoppingBagResponse>

    @POST("change-language")
    suspend fun changeLanguage(
        @Body changeLanguageRequestModel: ChangeLanguageRequestModel
    ): Response<ChangeLanguageResponse>
}