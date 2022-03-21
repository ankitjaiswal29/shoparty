package com.shoparty.android.ui.main.product_list

import com.shoparty.android.network.RetrofitBuilder


class ProductListRepository {
    suspend fun getProductListApi(request: ProductListRequestModel) =
        RetrofitBuilder.apiService?.getProductList(request)

}