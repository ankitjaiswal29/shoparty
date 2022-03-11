package com.shoparty.android.ui.main.wishlist

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
class WishListViewModel(private val app: Application) : ViewModel() {
    private val repository = WishListRepository()
    private val mWishlist = MutableLiveData<Resource<List<WishListResponse.Data>>>()
   // val category: LiveData<Resource<List<CategoryResponse.Category>>> = mCategory
    val wishlist: LiveData<Resource<List<WishListResponse.Data>>> = mWishlist
    fun getWishlist(request:WishListRequestModel) = viewModelScope.launch {
        if (Utils.hasInternetConnection(app.applicationContext)) {
            mWishlist.postValue(Resource.Loading())
            val response = repository.wishListApi(request)
            mWishlist.postValue(handleCategoryResponse(response!!))
        } else {
            mWishlist.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }

    private fun handleCategoryResponse(response: Response<WishListResponse>): Resource<List<WishListResponse.Data>> {
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