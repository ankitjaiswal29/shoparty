package com.shoparty.android.ui.filter

import com.shoparty.android.ui.filter.age.AgeRequest
import com.shoparty.android.ui.filter.age.AgeResponse
import com.shoparty.android.ui.filter.color.ColorsResponse
import com.shoparty.android.ui.filter.size.SizeResponse

interface QuantityListner {
   // fun onColorQuantitychanged(userlist: ArrayList<ColorsResponse.Colors>)
    fun onColorQuantitychanged(userlist: ArrayList<String>)
    fun onSizeQuantitychanged(userlist: ArrayList<String>)
    fun onGenderQuantitychanged(userlist: ArrayList<String>)
    fun onAgeQuantitychanged(userlist: ArrayList<AgeRequest>)
}
