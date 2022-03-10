package com.shoparty.android.ui.main.categories

import com.shoparty.android.network.RetrofitBuilder

/**
 * Created by Amit Gupta on 09-03-2022.
 */
class CategoryRepository {

    suspend fun categoryApi(request:CategoryRequestModel) =
        RetrofitBuilder.apiService?.getCategory(request)

}