package com.shoparty.android.ui.vouchers

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
import java.util.*


class VoucherViewModel(private val app: Application) : ViewModel()
{
    private var mContext:Context = app.applicationContext

    private val repository = VoucherRepository()
    private val mvoucher = MutableLiveData<Resource<List<VoucherListResponse.Data>>>()
    val voucher: LiveData<Resource<List<VoucherListResponse.Data>>> = mvoucher

    fun getVoucher() = viewModelScope.launch {
          if(Utils.hasInternetConnection(app.applicationContext))
            {
                mvoucher.postValue(Resource.Loading())
                val response = repository.getvoucher()
                mvoucher.postValue(handlegetVoucherResponse(response!!))
            }
            else
            {
                mvoucher.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }

    }

    private fun handlegetVoucherResponse(response: Response<VoucherListResponse>): Resource<List<VoucherListResponse.Data>> {
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