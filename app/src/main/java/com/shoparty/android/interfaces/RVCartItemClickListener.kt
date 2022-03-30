package com.shoparty.android.interfaces

import android.view.View

interface RVCartItemClickListener {
    fun onClick(pos: Int, view: View? = null)
    fun onPlus(pos: Int, view: View? = null)
    fun onMinus(pos: Int, view: View? = null)
    fun onClear(pos: Int, view: View? = null)
}
