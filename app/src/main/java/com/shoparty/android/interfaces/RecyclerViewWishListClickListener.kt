package com.shoparty.android.interfaces

import com.shoparty.android.ui.address.addaddress.getaddress.GetAddressListResponse
import com.shoparty.android.ui.main.wishlist.WishListResponse
import com.shoparty.android.ui.myorders.myorder.MyOrderResponse

interface RecyclerViewWishListClickListener {
    fun itemclick(id: String, ItemsViewModel: WishListResponse.Data)

}
