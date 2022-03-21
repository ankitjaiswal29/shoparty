package com.shoparty.android.ui.main.wishlist

import com.shoparty.android.network.RetrofitBuilder

class WishListRepository {

    suspend fun wishListApi(request:WishListRequestModel) =
        RetrofitBuilder.apiService?.getWishlist(request)

    suspend fun addremovewishListApi(request:RemoveWishListRequestModel) =
        RetrofitBuilder.apiService?.addremoveWishlist(request)

}