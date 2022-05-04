package com.shoparty.android.ui.main.mainactivity

import com.shoparty.android.network.RetrofitBuilder
import com.shoparty.android.ui.main.categories.CategoryRequestModel


/**
 * Created by Amit Gupta on 16-03-2022.
 */

class MainRepository {

    suspend fun drawerApi(request:CategoryRequestModel) =
        RetrofitBuilder.apiService?.getDrawer(request)

    suspend fun languageChangeApi(request: ChangeLanguageRequestModel) =
        RetrofitBuilder.apiService?.changeLanguage(request)
}