package com.shoparty.android.ui.main.product_list

import com.shoparty.android.ui.filter.age.AgeRequest

data class ProductListRequestModel(
    val language_id: String = "",
    val filter_applied: String = "",
    var filter: Filter = Filter(),
    val sort_applied: Int = 0,
    val sort_type: Int = 0,
) {
    data class Filter(
        var color: ArrayList<String> = ArrayList(),
        var size: ArrayList<String> = ArrayList(),
        var age:ArrayList<AgeRequest> = ArrayList(),
        var gender:ArrayList<String> = ArrayList(),
        var price:Price = Price(),
        var category_id:String="0")
    {
      data class Price(
          var from: Int=0,
          var to: Int=1000000)
    }
}


