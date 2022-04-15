package com.shoparty.android.ui.filter

import com.shoparty.android.ui.filter.color.ColorsResponse

interface QuantityListner {

    fun onQuantitychanged(userlist: ArrayList<ColorsResponse.Colors>)
}
