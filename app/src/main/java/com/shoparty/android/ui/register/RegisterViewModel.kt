package com.shoparty.android.ui.register

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.ApiResponse
import com.shoparty.android.utils.apiutils.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(private val app: Application) : ViewModel()
{
    private val repository = RegisterRepository()
    private val mSignUp = MutableLiveData<Resource<User>>()
    val signUp: LiveData<Resource<User>> = mSignUp

    fun postSignUp(request: RegisterRequestModel) = viewModelScope.launch {
        if (Utils.hasInternetConnection(app.applicationContext)) {
            mSignUp.postValue(Resource.Loading())
            val response = repository.postSignUp(request)
            mSignUp.postValue(handleSignUpResponse(response))
        } else {
            mSignUp.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleSignUpResponse(response: Response<RegisterResponseModel>): Resource<User> {
        if (response?.isSuccessful)
        {
            response.body()?.let { res ->
                return if (res.status)
                {
                    Resource.Success(res.message,res.data)
                }
                else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(response.message())
    }






}