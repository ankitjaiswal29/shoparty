package com.shoparty.android.interfaces

interface RecyclerViewAddressClickListener {
    fun editclick(address_id: Int)

    fun removeclick(address_id: Int, position: Int)
}
