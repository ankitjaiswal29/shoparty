package com.shoparty.android.ui.login

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
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

class LoginViewModel(private val app: Application) : ViewModel()
{
    private var mContext:Context = app.applicationContext
    private val repository = LoginRepository()
    val etMobileNo: ObservableField<String> = ObservableField()
    private val mlogin = MutableLiveData<Resource<LoginResponse.User>>()
    val login: LiveData<Resource<LoginResponse.User>> = mlogin

    fun postLogin() = viewModelScope.launch {
        if(validation())
        {
            val request = LoginRequestModel(etMobileNo.get()!!,Constants.DEVICE_TYPE,
                Constants.DEVICE_TOKEN,Constants.TYPE)
            if(Utils.hasInternetConnection(app.applicationContext))
            {
                mlogin.postValue(Resource.Loading())
                val response = repository.loginapi(request)
                mlogin.postValue(handleLoginResponse(response!!))
            }
            else
            {
                mlogin.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }
        }

    }
    
    
    private fun validation():Boolean
    {
        if (etMobileNo.get().isNullOrBlank()) {
            Utils.showShortToast(mContext,mContext.getString(R.string.entermobileno))
            return false
        }

        if(Utils.checkValidMobile(etMobileNo.get()!!))
        {
            Utils.showShortToast(mContext,mContext.getString(R.string.entervalidnumber))
            return false
        }
        return true
    }

    private fun handleLoginResponse(response: Response<LoginResponse>): Resource<LoginResponse.User> {
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