package com.shoparty.android.ui.shoppingbag

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shoparty.android.common_modal.Product
import com.shoparty.android.utils.apiutils.Resource

class ShoppingBagViewModel(private val app: Application) : ViewModel() {

    private val repository = ShoppingBagRepository()

    private val mProductList = MutableLiveData<Resource<ArrayList<Product>>>()
    val productList: LiveData<Resource<ArrayList<Product>>> = mProductList

//    fun searchProduct(requestModel: SearchRequestModel) =
//        viewModelScope.launch {
//            if (Utils.hasInternetConnection(app.applicationContext)) {
//                mProductList.postValue(Resource.Loading())
//                val response = repository.searchProduct(requestModel)
//                mProductList.postValue(handleSearchResponse(response!!))
//            } else {
//                mProductList.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
//            }
//        }
//    private fun handleSearchResponse(response: Response<SearchResponseModel>): Resource<ArrayList<Product>> {
//        if (response?.isSuccessful) {
//            response.body()?.let { res ->
//                return if (res.response_code == 200) {
//                    Resource.Success(res.message, res.home_result)
//                } else {
//                    Resource.Error(res.message)
//                }
//            }
//        }
//        return Resource.Error(response.message())
//    }

}