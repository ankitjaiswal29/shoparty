package com.shoparty.android.ui.shoppingbag

import com.shoparty.android.network.RetrofitBuilder


class ShoppingBagRepository {

    suspend fun shoppingBagList(request: ShoppingBagRequestModel) =
       RetrofitBuilder.apiService?.shoppingListAsync(request)


    suspend fun removeCartItem(request: CartItemRemoveRequestModel) =
        RetrofitBuilder.apiService?.deleteCartItemAsync(request)


    suspend fun storeList(request: ShoppingBagRequestModel) =
        RetrofitBuilder.apiService?.storeListAsync(request)
}