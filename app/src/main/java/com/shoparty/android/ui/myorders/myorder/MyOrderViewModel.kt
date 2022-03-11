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
    private var mContext:Context = app.applicationContext

    private val repository = MyOrderRepository()

   // val etFirstname: ObservableField<String> = ObservableField()

    private val mmyorders = MutableLiveData<Resource<List<MyOrderResponse.Data>>>()
    val myorder: LiveData<Resource<List<MyOrderResponse.Data>>> = mmyorders



    fun myOrders() = viewModelScope.launch {

        val request = MyOrderRequestModel("1")
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mmyorders.postValue(Resource.Loading())
            val response = repository.getmyorderapi(request)
            mmyorders.postValue(handleMyOrderResponse(response!!))
        }
        else
        {
            mmyorders.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }



    private fun handleMyOrderResponse(response: Response<MyOrderResponse>): Resource<List<MyOrderResponse.Data>>? {
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