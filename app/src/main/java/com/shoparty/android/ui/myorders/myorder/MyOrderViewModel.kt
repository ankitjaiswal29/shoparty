package com.shoparty.android.ui.myorders.myorder

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


class MyOrderViewModel(private val app: Application) : ViewModel()
{

    private val repository = MyOrderRepository()

    private val mMyOrders = MutableLiveData<Resource<List<MyOrderResponse.OrderHistory>>>()
    val myOrder: LiveData<Resource<List<MyOrderResponse.OrderHistory>>> = mMyOrders



    fun myOrders() = viewModelScope.launch {

        val request = MyOrderRequestModel("1")
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mMyOrders.postValue(Resource.Loading())
            val response = repository.getmyorderapi(request)
            mMyOrders.postValue(handleMyOrderResponse(response!!))
        }
        else
        {
            mMyOrders.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }



    private fun handleMyOrderResponse(response: Response<MyOrderResponse>): Resource<List<MyOrderResponse.OrderHistory>>? {
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