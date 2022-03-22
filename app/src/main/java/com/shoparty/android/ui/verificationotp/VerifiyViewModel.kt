package com.shoparty.android.ui.verificationotp

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

class VerifiyViewModel(private val app: Application) : ViewModel()
{
    private var mContext:Context = app.applicationContext
    private val repository = VerifiyRepository()

 //   val etOtp: ObservableField<String> = ObservableField()
    val editTextNumberPassword:ObservableField<String> = ObservableField()
   val editTextNumberPassword2:ObservableField<String> = ObservableField()
   val editTextNumberPassword3:ObservableField<String> = ObservableField()
    val editTextNumberPassword4:ObservableField<String> = ObservableField()
    private val mverifiy = MutableLiveData<Resource<VerifiyOtpResponse.User>>()
    val verifiyotp: LiveData<Resource<VerifiyOtpResponse.User>> = mverifiy


    private val mresend = MutableLiveData<Resource<ResendOtpResponse>>()
    val resendOtp: LiveData<Resource<ResendOtpResponse>> = mresend


    fun postVerifiy(userid:String) = viewModelScope.launch {
        if(validation())
        {
            val request = VerifiyRequestModel(editTextNumberPassword.get()+editTextNumberPassword2.get()+editTextNumberPassword3.get()+editTextNumberPassword4.get()!!,userid)
            if(Utils.hasInternetConnection(app.applicationContext))
            {
                mverifiy.postValue(Resource.Loading())
                val response = repository.verifiyapi(request)
                mverifiy.postValue(handleLoginResponse(response!!))
            }
            else
            {
                mverifiy.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }
        }

    }


    fun postResend(userid:String) = viewModelScope.launch {
            val request = ResendRequestModel(userid)
            if(Utils.hasInternetConnection(app.applicationContext))
            {
                mresend.postValue(Resource.Loading())
                val response = repository.resendapi(request)
                mresend.postValue(handleResendResponse(response!!))
            }
            else
            {
                mresend.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }

    }
    
    
    private fun validation():Boolean
    {
       /* if (etOtp.get().isNullOrBlank()) {
            Utils.showShortToast(mContext,mContext.getString(R.string.enterotp))
            return false
        }*/
        if (editTextNumberPassword.get().isNullOrBlank()) {
            Utils.showShortToast(mContext,mContext.getString(R.string.enterotp))
            return false
        }
        if (editTextNumberPassword2.get().isNullOrBlank()) {
            Utils.showShortToast(mContext,mContext.getString(R.string.enterotp))
            return false
        }
        if (editTextNumberPassword3.get().isNullOrBlank()) {
            Utils.showShortToast(mContext,mContext.getString(R.string.enterotp))
            return false
        }
        if (editTextNumberPassword4.get().isNullOrBlank()) {
            Utils.showShortToast(mContext,mContext.getString(R.string.enterotp))
            return false
        }
        return true
    }

    private fun handleLoginResponse(response: Response<VerifiyOtpResponse>): Resource<VerifiyOtpResponse.User> {
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


    private fun handleResendResponse(response: Response<ResendOtpResponse>): Resource<ResendOtpResponse>? {
        if (response?.isSuccessful)
        {
            response.body()?.let { res ->
                return if (res.response_code==200)
                {
                    Resource.Success(res.otp.toString())
                }
                else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(response.message())
    }
    
    
    






}