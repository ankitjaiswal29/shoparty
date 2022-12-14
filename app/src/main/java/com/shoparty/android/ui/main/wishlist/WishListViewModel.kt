package com.shoparty.android.ui.main.wishlist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class WishListViewModel(private val app: Application) : ViewModel() {
    private val repository = WishListRepository()
    private val mWishlist = MutableLiveData<Resource<List<WishListResponse.Result>>>()
    val wishlist: LiveData<Resource<List<WishListResponse.Result>>> = mWishlist

    private val maddremoveWishlist = MutableLiveData<Resource<RemoveWishlistResponse>>()
    val addremovewishlist: LiveData<Resource<RemoveWishlistResponse>> = maddremoveWishlist

    fun getWishlist(language:String) = viewModelScope.launch {
        if (Utils.hasInternetConnection(app.applicationContext))
        {
            val request = WishListRequestModel(language)
            mWishlist.postValue(Resource.Loading())
            val response = repository.wishListApi(request)
            mWishlist.postValue(handleWishlistResponse(response!!))
        } else {
            mWishlist.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }
    }

    fun addremoveWishlist(product_id:String, type:Int,product_detail_id:Int,product_size_id:String,product_colorId:String) = viewModelScope.launch {
        if (Utils.hasInternetConnection(app.applicationContext))
        {
            val request = RemoveWishListRequestModel(product_id,type,product_detail_id,product_size_id,product_colorId)
            maddremoveWishlist.postValue(Resource.Loading())
            val response = repository.addremovewishListApi(request)
            maddremoveWishlist.postValue(handleremoveWishlistResponse(response!!))
        } else {
            maddremoveWishlist.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }

    private fun handleWishlistResponse(response: Response<WishListResponse>): Resource<List<WishListResponse.Result>> {
        if (response?.isSuccessful == true) {
            response.body()?.let { res ->
                return if (res.response_code == 200) {
                    Resource.Success(res.message, res.result)
                }
                else if(res.response_code == 204)
                {
                    Resource.Success(res.message, res.result)
                }
                else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(response.message())
    }


    private fun handleremoveWishlistResponse(response: Response<RemoveWishlistResponse>): Resource<RemoveWishlistResponse> {
        if (response?.isSuccessful == true) {
            response.body()?.let { res ->
                return if (res.response_code == 200) {
                    Resource.Success(res.message)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(response.message())
    }
}