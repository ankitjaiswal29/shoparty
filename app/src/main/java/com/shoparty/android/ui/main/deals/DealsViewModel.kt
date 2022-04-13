package com.shoparty.android.ui.main.deals

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.common_modal.Product
import com.shoparty.android.ui.main.product_list.ProductListResponse
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsResponse
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
class DealsViewModel(private val app: Application) : ViewModel() {
    private val repository = DealsRepository()

    private val mDeals = MutableLiveData<Resource<List<Product>>>()
    val deals: LiveData<Resource<List<Product>>> = mDeals

    fun getDeals(request:DealsRequestModel) = viewModelScope.launch {
        if (Utils.hasInternetConnection(app.applicationContext)) {
            mDeals.postValue(Resource.Loading())
            val response = repository.dealsApi(request)
            mDeals.postValue(handleDealsResponse(response!!))
        } else {
            mDeals.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }


    private fun handleDealsResponse(response: Response<ProductListResponse>): Resource<List<Product>> {
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
}

