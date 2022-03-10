package com.shoparty.android.ui.contactus

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

class ContactUsViewModel(private val app: Application) : ViewModel()
{
    private var mContext:Context = app.applicationContext
    private val repository = ContactUsRepository()

    private val mcontactus = MutableLiveData<Resource<ContactUsResponse.Data>>()
    val contactus: LiveData<Resource<ContactUsResponse.Data>> = mcontactus

    fun getContactus() = viewModelScope.launch {
          if(Utils.hasInternetConnection(app.applicationContext))
            {
                mcontactus.postValue(Resource.Loading())
                val response = repository.contactusapi()
                mcontactus.postValue(handleContactUsResponse(response!!))
            }
            else
            {
                mcontactus.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }
    }
    private fun handleContactUsResponse(response: Response<ContactUsResponse>): Resource<ContactUsResponse.Data> {
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