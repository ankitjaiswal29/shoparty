package com.shoparty.android.utils

import android.app.Activity
import android.content.Context


import android.content.SharedPreferences



object PrefManager {
    fun init(context: Context) {
        if (!(::preferences.isInitialized)) {
            preferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
        }
    }
    const val AUTH_TOKEN: String = "authToken"
    const val USER_ID: String = "user_id"
    const val MOBILE: String = "MOBILE"
    const val NAME: String = "NAME"
    const val IMAGE: String = "IMAGE"
    const val EMAIL: String = "EMAIL"
    const val DOB: String = "DOB"
    const val STREET: String = "STREET"
    const val HOUSENO: String = "HOUSENO"
    const val GENDER: String = "GENDER"
    const val CITYID: String = "CITYID"
    const val MALE: String = "Male"
    const val IS_LOGIN: String = "isLogin"
    const val LANGUAGEID: String = "languageId"
    const val SORTSELECTEDNAME: String = "SORTSTATUS"
    const val IS_SHIPPING_PAGE: String = "IS_SHIPPING_PAGE"

    const val IDPRODUCT1: String = "idProduct"
    const val PRODUCATNAME1: String = "ProductName"
    const val PRODUCATDETAILSID1: String = "ProductDetailId"
    const val PRODUCTSIZEID1: String = "ProductSize"
    const val PRODUCTCOLORID1: String = "ProductColor"
    const val isFromLink: String = "isFromLink"

    private lateinit var preferences: SharedPreferences

    fun clearUserPref()
    {
        preferences.edit()?.remove(AUTH_TOKEN)?.apply()
        preferences.edit()?.remove(USER_ID)?.apply()
        preferences.edit()?.remove(NAME)?.apply()
    }

    //calling this method will clear FCM key
    fun clearAllPref() {
        preferences.edit()?.clear()?.apply()
    }

    fun read(key: String, defValue: String): String {
        return preferences.getString(key, defValue)!!
    }

    fun read(key: String, defValue: Boolean): Boolean {
        return preferences.getBoolean(key, defValue)
    }

    fun read(key: String, defValue: Long): Long {
        return preferences.getLong(key, defValue)
    }

    fun read(key: String, defValue: Int): Int {
        return preferences.getInt(key, defValue)
    }

    fun write(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun write(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    fun write(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    fun write(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }
}