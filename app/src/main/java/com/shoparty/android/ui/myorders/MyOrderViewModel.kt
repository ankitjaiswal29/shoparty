package com.shoparty.android.ui.myorders

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

import com.shoparty.android.ui.myorders.myorderlist.MyOrderRequestModel
import com.shoparty.android.ui.myorders.myorderlist.MyOrderResponse
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsRequestModel
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsResponse


class MyOrderViewModel(private val app: Application) : ViewModel()
{
    private val repository = MyOrderRepository()

    private val mMyOrders = MutableLiveData<Resource<List<MyOrderResponse.OrderHistory>>>()
    val myOrder: LiveData<Resource<List<MyOrderResponse.OrderHistory>>> = mMyOrders

    private val mOrdersdetails = MutableLiveData<Resource<OrderDetailsResponse.OrderList>>()
    val Orderdetails: LiveData<Resource<OrderDetailsResponse.OrderList>> = mOrdersdetails

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


    fun orderDetails(order_id: String) = viewModelScope.launch {
        val request = OrderDetailsRequestModel("1",order_id)
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mOrdersdetails.postValue(Resource.Loading())
            val response = repository.getorderdetailsapi(request)
            mOrdersdetails.postValue(handleOrderDetailsResponse(response!!))
        }
        else
        {
            mOrdersdetails.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
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

    private fun handleOrderDetailsResponse(response: Response<OrderDetailsResponse>): Resource<OrderDetailsResponse.OrderList>? {
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