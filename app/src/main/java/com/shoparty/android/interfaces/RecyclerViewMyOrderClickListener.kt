package com.shoparty.android.interfaces

import com.shoparty.android.ui.address.addaddress.getaddress.GetAddressListResponse
import com.shoparty.android.ui.myorders.myorder.MyOrderResponse

interface RecyclerViewMyOrderClickListener {
    fun itemclick(order_id: Int, ItemsViewModel: MyOrderResponse.Data)

}
