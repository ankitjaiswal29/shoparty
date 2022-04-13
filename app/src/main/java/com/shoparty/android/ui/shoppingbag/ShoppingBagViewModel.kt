package com.shoparty.android.ui.shoppingbag

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.common_modal.CartProduct
import com.shoparty.android.common_modal.Product
import com.shoparty.android.ui.productdetails.ProducatDetailsRequestModel
import com.shoparty.android.ui.productdetails.ProducatDetailsResponse
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ShoppingBagViewModel(private val app: Application) : ViewModel() {

    private val repository = ShoppingBagRepository()

    private val mCartItems = MutableLiveData<Resource<List<CartProduct>>>()
    val cartitems: LiveData<Resource<List<CartProduct>>> = mCartItems

    fun cartItemList(
        langauge_id: String
    ) = viewModelScope.launch {
        val request = ShoppingBagRequestModel(langauge_id)
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mCartItems.postValue(Resource.Loading())
            val response = repository.shoppingBagList(request)
            mCartItems.postValue(handleShoppingBagResponse(response!!))
        }
        else
        {
            mCartItems.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }

    private fun handleShoppingBagResponse(response: Response<ShoppingBagResponse>): Resource<List<CartProduct>> {
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