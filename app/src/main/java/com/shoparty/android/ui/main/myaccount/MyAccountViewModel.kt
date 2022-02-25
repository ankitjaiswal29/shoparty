package com.shoparty.android.ui.main.myaccount

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

class MyAccountViewModel(private val app: Application) : ViewModel()
{
    private var mContext:Context = app.applicationContext
    private val repository = MyAccountRepository()

    private val mlogout = MutableLiveData<Resource<LogoutResponse>>()
    val logout: LiveData<Resource<LogoutResponse>> = mlogout

    fun postLogout() = viewModelScope.launch {
            if(Utils.hasInternetConnection(app.applicationContext))
            {
                mlogout.postValue(Resource.Loading())
                val response = repository.logoutapi()
                mlogout.postValue(handleLoginResponse(response!!))
            }
            else
            {
                mlogout.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }


    }
    
    

    private fun handleLoginResponse(response: Response<LogoutResponse>): Resource<LogoutResponse> {
        if (response?.isSuccessful)
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