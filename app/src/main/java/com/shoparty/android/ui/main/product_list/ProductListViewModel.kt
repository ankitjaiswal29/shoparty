package com.shoparty.android.ui.main.product_list

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


class ProductListViewModel(private val app: Application) : ViewModel()
{
    private val repository = ProductListRepository()
    private val mProductList = MutableLiveData<Resource<List<Product>>>()
    val productList: LiveData<Resource<List<Product>>> = mProductList

    fun producatList(filter_id:String,type:String,langauge_id:String,user_id:String) = viewModelScope.launch {

        val request = ProductListRequestModel(filter_id,type,langauge_id,user_id)
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mProductList.postValue(Resource.Loading())
            val response = repository.getProductListApi(request)
            mProductList.postValue(handleProductListResponse(response!!))
        }
        else
        {
            mProductList.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }


    fun topSellingProducatList(langauge_id:String,type:String,offset:String,limit:String,user_id:String) = viewModelScope.launch {
        val request = TopSellingRequestModel(langauge_id,type,offset,limit,user_id)
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mProductList.postValue(Resource.Loading())
            val response = repository.getTopProductListApi(request)
            mProductList.postValue(handleProductListResponse(response!!))
        }
        else
        {
            mProductList.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }



    private fun handleProductListResponse(response: Response<ProductListResponse>): Resource<List<Product>>? {
        if (response?.isSuccessful)
        {
            response.body()?.let { res ->
                return if (res.response_code==200)
                {
                    Resource.Success(res.message,res.result)
                }
                else if(res.response_code==204)
                {
                    Resource.Success(res.message,res.result)
                }
                else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(response.message())
    }

}