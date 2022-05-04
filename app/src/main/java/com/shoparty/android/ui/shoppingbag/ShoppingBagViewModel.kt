package com.shoparty.android.ui.shoppingbag

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoparty.android.R
import com.shoparty.android.common_modal.CartProduct

import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ShoppingBagViewModel(private val app: Application) : ViewModel() {

    private val repository = ShoppingBagRepository()

    private val mCartItems = MutableLiveData<Resource<List<CartProduct>>>()
    val cartitems: LiveData<Resource<List<CartProduct>>> = mCartItems

    private val mCartItemsRemove = MutableLiveData<Resource<List<CartProduct>>>()
    val cartitemsremove: LiveData<Resource<List<CartProduct>>> = mCartItemsRemove

    private val mStoreList = MutableLiveData<Resource<List<StoreListResponse.Result>>>()
    val storeList: LiveData<Resource<List<StoreListResponse.Result>>> = mStoreList


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


    fun cartItemRemove(
        shopping_id: String,
    ) = viewModelScope.launch {
        val request = CartItemRemoveRequestModel(shopping_id)
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mCartItemsRemove.postValue(Resource.Loading())
            val response = repository.removeCartItem(request)
            mCartItemsRemove.postValue(handleShoppingBagResponse(response!!))
        }
        else
        {
            mCartItemsRemove.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }

    }


    fun storeList(langauge_id: String) = viewModelScope.launch {
        val request = ShoppingBagRequestModel(langauge_id)
        if(Utils.hasInternetConnection(app.applicationContext))
        {
            mStoreList.postValue(Resource.Loading())
            val response = repository.storeList(request)
            mStoreList.postValue(handleStoreList(response!!))
        }
        else
        {
            mStoreList.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
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
                else if(res.response_code==204)
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


    private fun handleStoreList(response: Response<StoreListResponse>): Resource<List<StoreListResponse.Result>> {
        if (response?.isSuccessful == true)
        {
            response.body()?.let { res ->
                return if (res.response_code==200)
                {
                    Resource.Success(res.message,res.result)
                }
                else if(res.response_code==204)
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