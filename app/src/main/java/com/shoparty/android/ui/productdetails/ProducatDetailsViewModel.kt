package com.shoparty.android.ui.productdetails

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.common_modal.CartProduct
import com.shoparty.android.ui.shoppingbag.ShoppingBagResponse
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

class ProducatDetailsViewModel(private val app: Application) : ViewModel()
{
    private var mContext:Context = app.applicationContext
    private val repository = ProducatDetailsRepository()

    private val mproduct = MutableLiveData<Resource<ProducatDetailsResponse.ProductData>>()
    val product: LiveData<Resource<ProducatDetailsResponse.ProductData>> = mproduct


    private val mAddBag = MutableLiveData<Resource<AddItemBagResponse>>()
    val addbag: LiveData<Resource<AddItemBagResponse>> = mAddBag



    fun postProducatDetails(
        langauge_id: String,
        product_detailsid: String,
        product_id: String,
        product_sizeId:String,
        product_colorId:String,
        user_id:String
    ) = viewModelScope.launch {
            val request = ProducatDetailsRequestModel(langauge_id,product_detailsid,product_id,
                product_sizeId,product_colorId,user_id)
            if(Utils.hasInternetConnection(app.applicationContext))
            {
                mproduct.postValue(Resource.Loading())
                val response = repository.producatdetailsapi(request)
                mproduct.postValue(handleProductDetailsResponse(response!!))
            }
            else
            {
                mproduct.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
            }

    }


    fun postAddProduct(
        product_id: String,
        product_detail_id: String,
        product_size_id: String,
        product_color_id:String,
        quantity:Int,
        price:String,
    ) = viewModelScope.launch {
        val request = AddItemToBagRequestModel(product_id,product_detail_id,product_size_id,
            product_color_id,quantity,price)
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mAddBag.postValue(Resource.Loading())
            val response = repository.addtobagapi(request)
            mAddBag.postValue(handleAddToBagResponse(response!!))
        }
        else
        {
            mAddBag.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }



    private fun handleProductDetailsResponse(response: Response<ProducatDetailsResponse>): Resource<ProducatDetailsResponse.ProductData> {
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

    private fun handleAddToBagResponse(response: Response<AddItemBagResponse>): Resource<AddItemBagResponse> {
        if (response?.isSuccessful == true)
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
    
    
    






}