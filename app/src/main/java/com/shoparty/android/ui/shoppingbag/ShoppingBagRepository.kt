package com.shoparty.android.ui.shoppingbag

import com.shoparty.android.network.RetrofitBuilder


class ShoppingBagRepository {

    suspend fun shoppingBagList(request: ShoppingBagRequestModel) =
       RetrofitBuilder.apiService?.shoppingListAsync(request)
}