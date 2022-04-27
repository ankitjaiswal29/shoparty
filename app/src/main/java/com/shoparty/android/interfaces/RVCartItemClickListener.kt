package com.shoparty.android.interfaces

import android.view.View
import android.widget.TextView

interface RVCartItemClickListener {
    fun onClick(pos: Int, view: View? = null)
    fun onPlus(pos: Int, view: View? = null)
    fun onMinus(pos: Int, view: View? = null, shoppingId: Int=0)
    fun onClear(pos: Int, view: View? = null)
}
