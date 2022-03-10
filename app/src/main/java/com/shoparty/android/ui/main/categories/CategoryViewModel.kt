package com.shoparty.android.ui.main.categories

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
/**
 * Created by Amit Gupta on 09-03-2022.
 */
class CategoryViewModel(private val app: Application) : ViewModel() {
    private val repository = CategoryRepository()
    private val mCategory = MutableLiveData<Resource<List<CategoryResponse.Category>>>()
    val category: LiveData<Resource<List<CategoryResponse.Category>>> = mCategory

    fun getCategory(request:CategoryRequestModel) = viewModelScope.launch {
        if (Utils.hasInternetConnection(app.applicationContext)) {
            mCategory.postValue(Resource.Loading())
            val response = repository.categoryApi(request)
            mCategory.postValue(handleCategoryResponse(response!!))
        } else {
            mCategory.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }


//    private fun validation():Boolean
//    {
//        if (etMobileNo.get().isNullOrBlank()) {
//            Utils.showShortToast(mContext,mContext.getString(R.string.entermobileno))
//            return false
//        }
//
//        if(Utils.checkValidMobile(etMobileNo.get()!!))
//        {
//            Utils.showShortToast(mContext,mContext.getString(R.string.entervalidnumber))
//            return false
//        }
//        return true
//    }

    private fun handleCategoryResponse(response: Response<CategoryResponse>): Resource<List<CategoryResponse.Category>> {
        if (response?.isSuccessful) {
            response.body()?.let { res ->
                return if (res.response_code == 200) {
                    Resource.Success(res.message, res.result)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(response.message())
    }

}