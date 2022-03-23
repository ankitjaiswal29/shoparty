package com.shoparty.android.common_modal

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "product")
data class Product(
    val age_from: String,
    val age_to: String,
    val ar_color_name: String,
    val ar_description: String,
    val ar_name: String,
    val brand_id: Int,
    val color_name: String,
    val cost_price: String,
    val created_at: String,
    val deleted_at: String,
    val discount: Int,
    val en_description: String,
    val en_name: String,
    val fav_status: Int,
    val gender: String,
    @PrimaryKey(autoGenerate = false) val id: Int,
    val image: String,
    val is_customizable: Int,
    val is_deliverable: Int,
    val product_color_id: Int,
    val product_descripion: String,
    val product_detail_id: Int,
    val product_id: Int,
    val product_image_id: Int,
    val product_name: String,
    val product_quantity_id: Int,
    val product_size_id: Int,
    val quantity: String,
    val sale_price: String,
    val season_id: String,
    val size: String,
    val status: Int,
    val tax: String,
    val tax_type: Int,
    val theme_id: String,
    val updated_at: String
):Parcelable