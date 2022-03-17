package com.shoparty.android.ui.main.mainactivity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Amit Gupta on 16-03-2022.
 */

@Parcelize
data class DrawerResponse(
    val message: String,
    val product_cat_result: List<Category>,
    val response_code: Int
) :Parcelable{
    @Parcelize
    data class Category(
        val ar_category_name: String,
        val category_name: String,
        val child_category: ArrayList<ChildCategory>,
        val id: Int
    ):Parcelable {
        @Parcelize
        data class ChildCategory(
            val ar_category_name: String,
            val category_name: String,
            val child_category: ArrayList<ChildCategoryX>,
            val id: Int
        ):Parcelable {
            @Parcelize
            data class ChildCategoryX(
                val ar_category_name: String,
                val category_name: String,
                val child_category: ArrayList<ChildCategoryXX>,
                val id: Int
            ):Parcelable {
                @Parcelize
                data class ChildCategoryXX(
                    val ar_category_name: String,
                    val category_name: String,
                    val id: Int
                ):Parcelable
            }
        }
    }
}
