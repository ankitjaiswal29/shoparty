package com.shoparty.android.ui.login

import android.app.Application
import android.content.Context
import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.ui.register.RegisterResponseModel
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val app: Application) : ViewModel()
{
    private var mContext:Context = app.applicationContext
    private val repository = LoginRepository()
    val fullName: ObservableField<String> = ObservableField()
    val etEmail: ObservableField<String> = ObservableField()
    val etMobileNo: ObservableField<String> = ObservableField()
    val tvDateOfBirth: ObservableField<String> = ObservableField()
    private val mlogin = MutableLiveData<Resource<RegisterResponseModel.User>>()
    val login: LiveData<Resource<RegisterResponseModel.User>> = mlogin
    fun postLogin(selectedGender: String) = viewModelScope.launch {
        if(validation())
        {
            val request = LoginRequestModel(fullName.get()!!,etEmail.get()!!,etMobileNo.get()!!,tvDateOfBirth.get()!!,selectedGender,Constants.DEVICE_TYPE,Constants.DEVICE_TOKEN)
            if(Utils.hasInternetConnection(app.applicationContext))
            {
                mlogin.postValue(Resource.Loading())
                val response = repository.loginapi(request)
                mlogin.postValue(handleSignUpResponse(response!!))
            }
            else
            {
                mlogin.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }
        }

    }
    
    
    private fun validation():Boolean
    {
        if (fullName.get().isNullOrBlank())
        {
            Utils.showShortToast(mContext,mContext.getString(R.string.enterfullname))
            return false
        }
        if (etEmail.get()?.trim().isNullOrBlank()) {
            Utils.showShortToast(mContext,mContext.getString(R.string.enteremailid))
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.get()?.trim()).matches())
        {
            Utils.showShortToast(mContext,mContext.getString(R.string.entervalidmail))
            return false
        }
        if (etMobileNo.get().isNullOrBlank()) {
            Utils.showShortToast(mContext,mContext.getString(R.string.entermobileno))
            return false
        }
        if(!Utils.checkValidMobile(etMobileNo.get()!!)){
            Utils.showShortToast(mContext,mContext.getString(R.string.entervalidnumber))
            return false
        }
        if(tvDateOfBirth.get().isNullOrBlank()){
            Utils.showShortToast(mContext,mContext.getString(R.string.pleaseselectdob))
            return false
        }
        return true
    }

    private fun handleSignUpResponse(response: Response<RegisterResponseModel>): Resource<RegisterResponseModel.User> {
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