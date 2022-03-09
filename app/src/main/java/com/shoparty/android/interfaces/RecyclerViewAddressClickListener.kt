package com.shoparty.android.interfaces

import com.shoparty.android.ui.address.addaddress.getaddress.GetAddressListResponse

interface RecyclerViewAddressClickListener {
    fun editclick(address_id: Int, ItemsViewModel: GetAddressListResponse.Data)

    fun removeclick(address_id: Int, position: Int)
}
