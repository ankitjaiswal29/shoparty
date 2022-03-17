package com.shoparty.android.ui.main.deals

/**
 * Created by Amit Gupta on 16-03-2022.
 */

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

class DealsViewModel(private val app: Application) : ViewModel() {
    private val repository = DealsRepository()
    private val mDeals = MutableLiveData<Resource<List<DealsResponse.Deals>>>()
    val deals: LiveData<Resource<List<DealsResponse.Deals>>> = mDeals

    fun getDeals(request:DealsRequestModel) = viewModelScope.launch {
        if (Utils.hasInternetConnection(app.applicationContext)) {
            mDeals.postValue(Resource.Loading())
            val response = repository.dealsApi(request)
            mDeals.postValue(handleDealsResponse(response!!))
        } else {
            mDeals.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }


    private fun handleDealsResponse(response: Response<DealsResponse>): Resource<List<DealsResponse.Deals>> {
        if (response?.isSuccessful) {
            response.body()?.let { res ->
                return if (res.response_code == 200) {
                    Resource.Success(res.message, res.result)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(response.message())

    }
}

