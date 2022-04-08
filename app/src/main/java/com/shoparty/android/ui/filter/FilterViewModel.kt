package com.shoparty.android.ui.filter

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.ui.filter.age.AgeResponse
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

import com.shoparty.android.ui.filter.color.ColorsRequestModel
import com.shoparty.android.ui.filter.color.ColorsResponse
import com.shoparty.android.ui.filter.gender.GenderResponse
import com.shoparty.android.ui.filter.size.SizeResponse


class FilterViewModel(private val app: Application) : ViewModel()
{

    private val repository = FilterRepository()
    private val mColors = MutableLiveData<Resource<List<ColorsResponse.Colors>>>()
    val mColor: LiveData<Resource<List<ColorsResponse.Colors>>> = mColors


    private val mSize = MutableLiveData<Resource<List<String>>>()
    val mSizes: LiveData<Resource<List<String>>> = mSize

    val mGender = MutableLiveData<Resource<List<String>>>()
    val mGenders: LiveData<Resource<List<String>>> = mGender

    val mAge = MutableLiveData<Resource<List<AgeResponse.Result>>>()
    val mAges: LiveData<Resource<List<AgeResponse.Result>>> = mAge

    fun colors() = viewModelScope.launch {
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

    fun sizes() = viewModelScope.launch {
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

    fun gender() = viewModelScope.launch {
        if (Utils.hasInternetConnection(app.applicationContext)) {
            mGender.postValue(Resource.Loading())
            val response = repository.getGenderApi()
            mGender.postValue(handleGenderResponse(response!!))
        } else {
            mGender.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }
    }

    fun age() = viewModelScope.launch {
        if (Utils.hasInternetConnection(app.applicationContext)) {
            mAge.postValue(Resource.Loading())
            val response = repository.ageApi()
            mAge.postValue(handleAgeResponse(response!!))
        } else {
            mAge.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }
    }



    private fun handleColorsResponse(response: Response<ColorsResponse>): Resource<List<ColorsResponse.Colors>>? {
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

    private fun handleSizeResponse(response: Response<SizeResponse>): Resource<List<String>>? {
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

    private fun handleGenderResponse(response: Response<GenderResponse>): Resource<List<String>>? {
        if (response?.isSuccessful == true) {
            response.body()?.let { res ->
                return if (res.response_code == 200) {
                    Resource.Success(res.message, res.result)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleAgeResponse(response: Response<AgeResponse>): Resource<List<AgeResponse.Result>>? {
        if (response?.isSuccessful == true) {
            response.body()?.let { res ->
                return if (res.response_code == 200) {
                    Resource.Success(res.message, res.result)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(response.message())

    }

}