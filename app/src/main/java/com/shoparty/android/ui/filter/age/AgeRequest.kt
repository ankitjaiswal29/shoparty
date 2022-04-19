package com.shoparty.android.ui.filter.age

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AgeRequest(
    val age_from: String,
    val age_to: String,
):Parcelable
