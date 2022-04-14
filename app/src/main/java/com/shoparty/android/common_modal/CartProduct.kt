package com.shoparty.android.common_modal

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "cart_product")
data class CartProduct(
    var age_from: String? = "",
    var age_to: String? = "",
    var ar_color_name: String? = "",
    var ar_description: String? = "",
    var ar_name: String? = "",
    var brand_id: Int,
    var color_name: String? = "",
    var cost_price: String = "",
    var created_at: String? = "",
    var deleted_at: String? = "",
    var discount: String,
    var en_description: String? = "",
    var en_name: String? = "",
    var fav_status: Int,
    var gender: String? = "",
    @PrimaryKey(autoGenerate = false) var id: Int,
    var image: String? = "",
    var is_customizable: Int = 0,
    var is_deliverable: Int = 0,
    var product_color_id: Int = 0,
    var product_descripion: String? = "",
    var product_detail_id: Int = 0,
    var product_id: Int = 0,
    var product_image_id: Int = 0,
    var product_name: String? = "",
    var product_quantity_id: Int = 0,
    var product_size_id: Int = 0,
    var quantity: String ,
    var sale_price: String ,
    var season_id: String? = "",
    var shopping_price: String = "",
    var shopping_qnty: String = "",
    var size: String? = "",
    var status: Int = 0,
    var tax: String,
    var tax_type: Int = 0,
    var theme_id: String? = "",
    var updated_at: String? = "",
    var bitmap: Bitmap? = null
) : Parcelable {
}