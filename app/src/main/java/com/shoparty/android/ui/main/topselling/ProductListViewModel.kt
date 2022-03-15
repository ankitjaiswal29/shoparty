package com.shoparty.android.ui.main.topselling

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.ui.address.addaddress.addaddress.*
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressRequestModel
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressResponse
import com.shoparty.android.ui.address.addaddress.getaddress.GetAddressListResponse
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource

import kotlinx.coroutines.launch
import retrofit2.Response
import android.text.Editable

import com.google.android.material.internal.TextWatcherAdapter

import android.text.TextWatcher
import java.util.*


class ProductListViewModel(private val app: Application) : ViewModel()
{

    private val repository = ProductListRepository()

    private val mProductList = MutableLiveData<Resource<List<ProductListResponse.ProductList>>>()
    val productList: LiveData<Resource<List<ProductListResponse.ProductList>>> = mProductList



    fun myOrders(productlist:String) = viewModelScope.launch {

        val request = ProductListRequestModel("1",productlist,"2")
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mProductList.postValue(Resource.Loading())
            val response = repository.getProductListApi(request)
            mProductList.postValue(handleMyOrderResponse(response!!))
        }
        else
        {
            mProductList.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }



    private fun handleMyOrderResponse(response: Response<ProductListResponse>): Resource<List<ProductListResponse.ProductList>>? {
        if (response?.isSuccessful)
        {
            response.body()?.let { res ->
                return if (res.response_code==200)
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