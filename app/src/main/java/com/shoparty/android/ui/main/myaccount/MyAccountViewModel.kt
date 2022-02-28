package com.shoparty.android.ui.main.myaccount

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

class MyAccountViewModel(private val app: Application) : ViewModel()
{

    private val repository = MyAccountRepository()

    private val mlogout = MutableLiveData<Resource<LogoutResponse>>()
    val logout: LiveData<Resource<LogoutResponse>> = mlogout

    private val mgetprofile = MutableLiveData<Resource<getProfileResponse.User>>()
    val getprofile: LiveData<Resource<getProfileResponse.User>> = mgetprofile

    fun postLogout() = viewModelScope.launch {
            if(Utils.hasInternetConnection(app.applicationContext))
            {
                mlogout.postValue(Resource.Loading())
                val response = repository.logoutapi()
                mlogout.postValue(handleLogoutResponse(response!!))
            }
            else
            {
                mlogout.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }
    }

    fun getProfle() = viewModelScope.launch {
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mgetprofile.postValue(Resource.Loading())
            val response = repository.getprofileapi()
            mgetprofile.postValue(handlegetProfileResponse(response!!))
        }
        else
        {
            mgetprofile.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }
    }


    
    

    private fun handleLogoutResponse(response: Response<LogoutResponse>): Resource<LogoutResponse> {
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

    private fun handlegetProfileResponse(response: Response<getProfileResponse>): Resource<getProfileResponse.User> {
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