package com.shoparty.android.ui.filter

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.ui.address.addaddress.addaddress.*
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressRequestModel
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressResponse
import com.shoparty.android.ui.address.addaddress.getaddress.GetAddressListResponse
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource

import kotlinx.coroutines.launch
import retrofit2.Response
import android.text.Editable

import com.google.android.material.internal.TextWatcherAdapter

import android.text.TextWatcher
import com.shoparty.android.ui.filter.size.SizeResponse
import java.util.*


class ColorsViewModel(private val app: Application) : ViewModel()
{

    private val repository = ColorsRepository()

    private val mColors = MutableLiveData<Resource<List<ColorsResponse.Colors>>>()
    val mColor: LiveData<Resource<List<ColorsResponse.Colors>>> = mColors


    private val mSize = MutableLiveData<Resource<List<String>>>()
    val mSizes: LiveData<Resource<List<String>>> = mSize

    fun Colors() = viewModelScope.launch {

        val request = ColorsRequestModel("1")
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mColors.postValue(Resource.Loading())
            val response = repository.getColorsApi(request)
            mColors.postValue(handleColorsResponse(response!!))
        }
        else
        {
            mColors.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }

    fun Sizes() = viewModelScope.launch {

      //  val request = ColorsRequestModel("1")
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mSize.postValue(Resource.Loading())
            val response = repository.getSizeApi()
            mSize.postValue(handleSizeResponse(response!!))
        }
        else
        {
            mSize.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }



    private fun handleColorsResponse(response: Response<ColorsResponse>): Resource<List<ColorsResponse.Colors>>? {
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

    private fun handleSizeResponse(response: Response<SizeResponse>): Resource<List<String>>? {
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