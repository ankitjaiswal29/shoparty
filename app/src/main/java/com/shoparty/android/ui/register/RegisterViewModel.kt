package com.shoparty.android.ui.register

import android.app.Application
import android.content.Context
import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(private val app: Application) : ViewModel()
{
    private var mContext:Context = app.applicationContext
    private val repository = RegisterRepository()
    val fullName: ObservableField<String> = ObservableField()
    val etEmail: ObservableField<String> = ObservableField()
    val etMobileNo: ObservableField<String> = ObservableField()
    val tvDateOfBirth: ObservableField<String> = ObservableField()
    private val mSignUp = MutableLiveData<Resource<RegisterResponseModel.User>>()
    val signUp: LiveData<Resource<RegisterResponseModel.User>> = mSignUp
    fun postSignUp(selectedGender: String, condition_checkable: Boolean) = viewModelScope.launch {
        if(validation(condition_checkable))
        {
            val request = RegisterRequestModel(fullName.get()!!,etEmail.get()!!,
                etMobileNo.get()!!,tvDateOfBirth.get()!!,selectedGender,Constants.DEVICE_TYPE,
                PrefManager.read(PrefManager.DEVICETOKEN,""))
            if(Utils.hasInternetConnection(app.applicationContext))
            {
                mSignUp.postValue(Resource.Loading())
                val response = repository.postSignUp(request)
                mSignUp.postValue(handleSignUpResponse(response!!))
            }
            else
            {
                mSignUp.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }
        }

    }
    
    
    private fun validation(condition_checkable: Boolean):Boolean
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

        if(Utils.checkValidMobile(etMobileNo.get()!!)){
            Utils.showShortToast(mContext,mContext.getString(R.string.entervalidnumber))
            return false
        }
        if(tvDateOfBirth.get().isNullOrBlank()){
            Utils.showShortToast(mContext,mContext.getString(R.string.pleaseselectdob))
            return false
        }
        if (!condition_checkable)
        {
            Utils.showShortToast(mContext,mContext.getString(R.string.pleasechecktermacondition))
            return false
        }
        return true
    }

    private fun handleSignUpResponse(response: Response<RegisterResponseModel>): Resource<RegisterResponseModel.User> {
        if (response?.isSuccessful == true)
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