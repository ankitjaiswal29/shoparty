package com.shoparty.android.common_modal

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = false) val category_id: Int?,
    val category_name: String):Parcelable


