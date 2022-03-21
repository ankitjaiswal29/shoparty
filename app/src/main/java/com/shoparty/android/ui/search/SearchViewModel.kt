package com.shoparty.android.ui.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.common_modal.Product
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(private val app: Application) : ViewModel() {

    private val repository = SearchRepository()

    private val mProductList = MutableLiveData<Resource<ArrayList<Product>>>()
    val productList: LiveData<Resource<ArrayList<Product>>> = mProductList

    fun searchProduct(requestModel: SearchRequestModel) =
        viewModelScope.launch {
            if (Utils.hasInternetConnection(app.applicationContext)) {
                mProductList.postValue(Resource.Loading())
                val response = repository.searchProduct(requestModel)
                mProductList.postValue(handleSearchResponse(response!!))
            } else {
                mProductList.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }
        }
    private fun handleSearchResponse(response: Response<SearchResponseModel>): Resource<ArrayList<Product>> {
        if (response?.isSuccessful) {
            response.body()?.let { res ->
                return if (res.response_code == 200) {
                    Resource.Success(res.message, res.home_result)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(response.message())
    }

}