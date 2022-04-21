package com.shoparty.android.ui.payment.orderplaced

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.common_modal.CartProduct
import com.shoparty.android.ui.shoppingbag.ShoppingBagResponse
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

class OrderPlacedViewModel(private val app: Application) : ViewModel()
{
    private var mContext:Context = app.applicationContext
    private val repository = OrderPlacedRepository()

    private val morderplacedsucess = MutableLiveData<Resource<OrderPlacedSuccessResponse.OrderSucessPlaced>>()
    val orderplacedsucess: LiveData<Resource<OrderPlacedSuccessResponse.OrderSucessPlaced>> = morderplacedsucess

    fun postOrderPlaced(
        langauge_id: String,
        shopping_id: ArrayList<String>,
        payment_status: String,
        payment_type: String,
        transaction_id: String,
        order_type: String,
        promocode_id: String,
        summaryamount: String,
        discount: String,
        tax: String,
        total_amount: String,
        address_id: String,
        is_deliverable: String,
        store_id: String,
    ) = viewModelScope.launch {
            val request = OrderPlacedRequestModel(langauge_id,shopping_id,payment_status,
                payment_type,transaction_id,order_type,promocode_id,summaryamount,discount,tax,
                total_amount,address_id,is_deliverable,store_id)
            if(Utils.hasInternetConnection(app.applicationContext))
            {
                morderplacedsucess.postValue(Resource.Loading())
                val response = repository.orderplacedapi(request)
                morderplacedsucess.postValue(handleOrderPlacedResponse(response!!))
            }
            else
            {
                morderplacedsucess.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }

    }
    private fun handleOrderPlacedResponse(response: Response<OrderPlacedSuccessResponse>): Resource<OrderPlacedSuccessResponse.OrderSucessPlaced> {
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

}