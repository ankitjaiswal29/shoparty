package com.shoparty.android.ui.filter

data class FilterModel(val name : String ="",
                       val description : List<String>,
                       var expand : Boolean = false)
