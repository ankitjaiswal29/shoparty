package com.shoparty.android.interfaces

import android.view.View

interface RecyclerViewItemClickListener {
    fun onClick(pos: String,view :View? = null)
}
