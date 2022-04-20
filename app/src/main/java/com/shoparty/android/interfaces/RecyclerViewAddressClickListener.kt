package com.shoparty.android.interfaces

import com.shoparty.android.ui.address.addaddress.getaddress.GetAddressListResponse

interface RecyclerViewAddressClickListener {
    fun editclick(address_id: Int, itemsViewModel: GetAddressListResponse.AddressData)

    fun removeclick(address_id: Int, position: Int)
    fun addressitemclick(address_id: Int, fulladdress: String)
}
