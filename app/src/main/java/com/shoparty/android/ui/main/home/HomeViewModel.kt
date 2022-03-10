package com.shoparty.android.ui.main.home

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

class HomeViewModel(private val app: Application) : ViewModel() {

    private val repository = HomeRepository()

    private val mDashboardResponseModel = MutableLiveData<Resource<HomeResponse.Home>>()
    val dashboardResponse: LiveData<Resource<HomeResponse.Home>> = mDashboardResponseModel


    fun getDashboardData(requestModel: HomeRequestModel) = viewModelScope.launch {
        if (Utils.hasInternetConnection(app.applicationContext)) {
            mDashboardResponseModel.postValue(Resource.Loading())
            val response = repository.getDashboardData(requestModel)
            mDashboardResponseModel.postValue(handleDashboardResponse(response))
        } else {
            mDashboardResponseModel.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleDashboardResponse(response: Response<HomeResponse>?): Resource<HomeResponse.Home>? {
        if (response?.isSuccessful!!) {
            response.body()?.let { res ->
                return if (res.response_code == 200) {
                    Resource.Success(res.message, res.result)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(app.resources.getString(R.string.something_went_wrong))
    }

}