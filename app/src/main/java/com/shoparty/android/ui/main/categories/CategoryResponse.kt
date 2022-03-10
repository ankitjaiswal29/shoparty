package com.shoparty.android.ui.main.categories

/**
 * Created by Amit Gupta on 09-03-2022.
 */
data class CategoryResponse(
    val message: String,
    val response_code: Int,
    val result: List<Category>
) {
    data class Category(
        val category_id: String,
        val category_name: String,
        val category_image: String,
    )
}