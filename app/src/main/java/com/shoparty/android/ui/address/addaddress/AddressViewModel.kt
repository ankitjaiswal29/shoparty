package com.shoparty.android.ui.address.addaddress

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.ui.address.addaddress.addaddress.AddAddressResponse
import com.shoparty.android.ui.address.addaddress.addaddress.AddAddressRequestModel
import com.shoparty.android.ui.address.addaddress.addaddress.getCountryResponse
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressRequestModel
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressResponse
import com.shoparty.android.ui.address.addaddress.getaddress.GetAddressListResponse
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

class AddressViewModel(private val app: Application) : ViewModel()
{
    private var mContext:Context = app.applicationContext

    private val repository = AddressRepository()

    val etFirstname: ObservableField<String> = ObservableField()
    val etLasttName: ObservableField<String> = ObservableField()
    val etStreatLandmark: ObservableField<String> = ObservableField()
    val etCity: ObservableField<String> = ObservableField()
    val etBuildingnoApartment: ObservableField<String> = ObservableField()
    val etMobile: ObservableField<String> = ObservableField()

    private val maddaddress = MutableLiveData<Resource<AddAddressResponse.Data>>()
    val address: LiveData<Resource<AddAddressResponse.Data>> = maddaddress

    private val mgetcountry = MutableLiveData<Resource<List<getCountryResponse.Data>>>()
    val getcountry: LiveData<Resource<List<getCountryResponse.Data>>> = mgetcountry


    private val mgetaddresslist = MutableLiveData<Resource<List<GetAddressListResponse.Data>>>()
    val getaddress: LiveData<Resource<List<GetAddressListResponse.Data>>> = mgetaddresslist


    private val mdeleteaddress = MutableLiveData<Resource<DeleteAddressResponse>>()
    val deleteaddress: LiveData<Resource<DeleteAddressResponse>> = mdeleteaddress

    fun postaddAddress(country_id:String) = viewModelScope.launch {
        if(validation())
        {
            val request = AddAddressRequestModel(etFirstname.get()!!,etLasttName.get()!!,country_id,
                     etStreatLandmark.get()!!
                    ,etCity.get()!!,etBuildingnoApartment.get()!!,etMobile.get()!!)
            if(Utils.hasInternetConnection(app.applicationContext))
            {
                maddaddress.postValue(Resource.Loading())
                val response = repository.addaddressapi(request)
                maddaddress.postValue(handleAddAddressResponse(response!!))
            }
            else
            {
                maddaddress.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }
        }

    }



    fun getcountrylist() = viewModelScope.launch {
          if(Utils.hasInternetConnection(app.applicationContext))
            {
                mgetcountry.postValue(Resource.Loading())
                val response = repository.getcountryapi()
                mgetcountry.postValue(handlegetCountryResponse(response!!))
            }
            else
            {
                mgetcountry.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }

    }

    fun getaddresslist() = viewModelScope.launch {
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mgetaddresslist.postValue(Resource.Loading())
            val response = repository.getaddressapi()
            mgetaddresslist.postValue(handlegetAddressResponse(response!!))
        }
        else
        {
            mgetaddresslist.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }

    fun deleteAddress(address_id:Int) = viewModelScope.launch {

          val request= DeleteAddressRequestModel(address_id)
          if(Utils.hasInternetConnection(app.applicationContext))
            {
                mdeleteaddress.postValue(Resource.Loading())
                val response = repository.deleteaddressapi(request)
                mdeleteaddress.postValue(handleDeleteAddressResponse(response!!))
            }
            else
            {
                mdeleteaddress.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }


    }
    
    
    private fun validation():Boolean
    {
        if(etFirstname.get().isNullOrBlank()) {
            Utils.showShortToast(mContext,mContext.getString(R.string.enterfirstname))
            return false
        }
        if(etLasttName.get().isNullOrBlank()) {
            Utils.showShortToast(mContext,mContext.getString(R.string.enterlastname))
            return false
        }
        if(etCity.get().isNullOrBlank()) {
            Utils.showShortToast(mContext,mContext.getString(R.string.entercity))
            return false
        }
        if(etStreatLandmark.get().isNullOrBlank()) {
            Utils.showShortToast(mContext,mContext.getString(R.string.enterlandmark))
            return false
        }
        if(etBuildingnoApartment.get().isNullOrBlank()) {
            Utils.showShortToast(mContext,mContext.getString(R.string.enterbuilding))
            return false
        }


        if(etMobile.get().isNullOrBlank()) {
            Utils.showShortToast(mContext,mContext.getString(R.string.entermobileno))
            return false
        }
        if(Utils.checkValidMobile(etMobile.get()!!))
        {
            Utils.showShortToast(mContext,mContext.getString(R.string.entervalidnumber))
            return false
        }
        return true
    }

    private fun handleAddAddressResponse(response: Response<AddAddressResponse>): Resource<AddAddressResponse.Data> {
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

    private fun handleDeleteAddressResponse(response: Response<DeleteAddressResponse>): Resource<DeleteAddressResponse> {
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

    private fun handlegetCountryResponse(response: Response<getCountryResponse>): Resource<List<getCountryResponse.Data>>? {
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

    private fun handlegetAddressResponse(response: Response<GetAddressListResponse>): Resource<List<GetAddressListResponse.Data>>? {
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