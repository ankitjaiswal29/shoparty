package com.shoparty.android.ui.myorders

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.ui.myorders.cancelorder.cancelorder.CancelReasonResponse
import com.shoparty.android.ui.myorders.cancelorder.cancelorder.OrderCancelSucessRequestModel
import com.shoparty.android.ui.myorders.cancelorder.cancelorder.OrderCancelSuccessfully
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

import com.shoparty.android.ui.myorders.myorderlist.MyOrderRequestModel
import com.shoparty.android.ui.myorders.myorderlist.MyOrderResponse
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsRequestModel
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsResponse
import com.shoparty.android.utils.PrefManager


class MyOrderViewModel(private val app: Application) : ViewModel()
{
    private val repository = MyOrderRepository()

    private val mMyOrders = MutableLiveData<Resource<List<MyOrderResponse.OrderHistory>>>()
    val myOrder: LiveData<Resource<List<MyOrderResponse.OrderHistory>>> = mMyOrders

    private val mOrdersdetails = MutableLiveData<Resource<OrderDetailsResponse.OrderList>>()
    val Orderdetails: LiveData<Resource<OrderDetailsResponse.OrderList>> = mOrdersdetails


    private val mCancelReason = MutableLiveData<Resource<List<CancelReasonResponse.Result>>>()
    val CancelReason: LiveData<Resource<List<CancelReasonResponse.Result>>> = mCancelReason


    private val mOrderCancelSucces = MutableLiveData<Resource<OrderCancelSuccessfully>>()
    val orderCancelSucces: LiveData<Resource<OrderCancelSuccessfully>> = mOrderCancelSucces

    fun myOrders() = viewModelScope.launch {

        val request = MyOrderRequestModel(PrefManager.read(PrefManager.LANGUAGEID, 1).toString())
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


    fun orderDetails(order_id: Int) = viewModelScope.launch {
        val request = OrderDetailsRequestModel(PrefManager.read(PrefManager.LANGUAGEID, 1).toString(),order_id)
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


    fun cancelReason() = viewModelScope.launch {
        val request = MyOrderRequestModel(PrefManager.read(PrefManager.LANGUAGEID, 1).toString())
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mCancelReason.postValue(Resource.Loading())
            val response = repository.cancelreasonapi(request)
            mCancelReason.postValue(handleCancelReasonResponse(response!!))
        }
        else
        {
            mMyOrders.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }


    fun cancelOrderSuccess(cancellation_reason:String,order_id:String) = viewModelScope.launch {
        val request = OrderCancelSucessRequestModel(cancellation_reason,order_id)
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mOrderCancelSucces.postValue(Resource.Loading())
            val response = repository.ordercancelapi(request)
            mOrderCancelSucces.postValue(handleCancelOrderResponse(response!!))
        }
        else
        {
            mOrderCancelSucces.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }



    private fun handleMyOrderResponse(response: Response<MyOrderResponse>): Resource<List<MyOrderResponse.OrderHistory>>? {
        if (response?.isSuccessful == true)
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
    private fun handleCancelReasonResponse(response: Response<CancelReasonResponse>): Resource<List<CancelReasonResponse.Result>>? {
        if (response?.isSuccessful == true)
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
        if (response?.isSuccessful == true)
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

    private fun handleCancelOrderResponse(response: Response<OrderCancelSuccessfully>): Resource<OrderCancelSuccessfully>? {
        if (response?.isSuccessful == true)
        {
            response.body()?.let { res ->
                return if (res.response_code==200)
                {
                    Resource.Success(res.message)
                }
                else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(response.message())
    }
}