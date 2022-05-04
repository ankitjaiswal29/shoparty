package com.shoparty.android.ui.notification

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

class NotificationListViewModel(private val app: Application) : ViewModel() {
    private val repository = NotificationRepository()
    private val mNotificationList = MutableLiveData<Resource<List<NotificationListResponse.Result>>>()
    val notificationlist: LiveData<Resource<List<NotificationListResponse.Result>>> = mNotificationList


    fun notificationList(langauge_id: String) = viewModelScope.launch {
        val request = NotificationListRequestModel(langauge_id)
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mNotificationList.postValue(Resource.Loading())
            val response = repository.notificationList(request)
            mNotificationList.postValue(handleNotificationListResponse(response!!))
        }
        else
        {
            mNotificationList.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }




    private fun handleNotificationListResponse(response: Response<NotificationListResponse>):
            Resource<List<NotificationListResponse.Result>> {
        if (response?.isSuccessful == true)
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