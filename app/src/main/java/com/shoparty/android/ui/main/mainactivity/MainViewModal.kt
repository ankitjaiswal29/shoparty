package com.shoparty.android.ui.main.mainactivity

/**
 * Created by Amit Gupta on 16-03-2022.
 */

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.ui.main.categories.CategoryRequestModel
import com.shoparty.android.ui.main.drawer.drawer_main_category.DrawerResponse
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModal(private val app: Application) : ViewModel() {
    private val repository = MainRepository()
    private val mDrawer = MutableLiveData<Resource<List<DrawerResponse.Category>>>()
    val drawer: LiveData<Resource<List<DrawerResponse.Category>>> = mDrawer

    fun getCategory(request: CategoryRequestModel) = viewModelScope.launch {
        if (Utils.hasInternetConnection(app.applicationContext)) {
            mDrawer.postValue(Resource.Loading())
            val response = repository.drawerApi(request)
            mDrawer.postValue(handleDrawerResponse(response!!))
        } else {
            mDrawer.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleDrawerResponse(response: Response<DrawerResponse>): Resource<List<DrawerResponse.Category>> {
        if (response.isSuccessful) {
            response.body()?.let { res ->
                return if (res.response_code == 200) {
                    Resource.Success(res.message, res.product_cat_result)
                } else {
                    Resource.Error(res.message)

                }
            }
        }

        return Resource.Error(response.message())

    }
}




