package com.shoparty.android.ui.main.wishlist

import com.shoparty.android.network.RetrofitBuilder

/**
 * Created by Amit Gupta on 09-03-2022.
 */
class WishListRepository {

    suspend fun wishListApi(request:WishListRequestModel) =
        RetrofitBuilder.apiService?.getWishlist(request)

}