package com.shoparty.android.ui.main.myaccount

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.ui.main.myaccount.getprofile.GetProfileResponse
import com.shoparty.android.ui.main.myaccount.logout.LogoutResponse
import com.shoparty.android.ui.main.myaccount.myprofileupdate.MyProfileUpdateResponse
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource

import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Response

class MyAccountViewModel(private val app: Application) : ViewModel()
{
    private val repository = MyAccountRepository()
    private var mContext: Context = app.applicationContext

    private val mlogout = MutableLiveData<Resource<LogoutResponse>>()
    val logout: LiveData<Resource<LogoutResponse>> = mlogout

    private val mgetprofile = MutableLiveData<Resource<GetProfileResponse.User>>()
    val getprofile: LiveData<Resource<GetProfileResponse.User>> = mgetprofile

    private val mprofileupdate = MutableLiveData<Resource<MyProfileUpdateResponse.User>>()
    val profileupdate: LiveData<Resource<MyProfileUpdateResponse.User>> = mprofileupdate



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

    fun postupdateProfile(requestBody: RequestBody) = viewModelScope.launch {
           if(Utils.hasInternetConnection(app.applicationContext))
            {
                mprofileupdate.postValue(Resource.Loading())
                val response = repository.updateprofileapi(requestBody)
                mprofileupdate.postValue(handleupdateProfileResponse(response!!))
            }
            else
            {
                mprofileupdate.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
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

    private fun handlegetProfileResponse(response: Response<GetProfileResponse>): Resource<GetProfileResponse.User> {
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


    private fun handleupdateProfileResponse(response: Response<MyProfileUpdateResponse>): Resource<MyProfileUpdateResponse.User> {
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