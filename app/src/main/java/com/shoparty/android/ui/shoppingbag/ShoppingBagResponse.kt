package com.shoparty.android.ui.shoppingbag

import com.shoparty.android.common_modal.CartProduct

data class ShoppingBagResponse(
    val message: String,
    val response_code: Int,
    val result: List<CartProduct>)
{

}