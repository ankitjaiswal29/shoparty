package com.shoparty.android.utils

import android.app.Activity
import android.content.Context


import com.shoparty.android.app.CustomApplication
import android.content.SharedPreferences



object PrefManager {
    fun init(context: Context) {
        if (!(::preferences.isInitialized)) {
            preferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
        }
    }
    const val AUTH_TOKEN: String = "authToken"
    const val USER_ID: String = "user_id"
    const val NAME: String = "name"
    const val PHONE: String = "phone_number"
    const val GENDER: String = "gender"
    const val COUNTRY_NAME: String = "country_name"
    const val COUNTRY_ID: String = "country_id"
    const val COUNTRY_CODE: String = "country_code"
    const val IMAGE: String = "image"
    const val ADDRESS: String = "address"
    const val ROLE: String = "role"
    const val OTP: String = "otp"
    const val Logged_User: String = "loggedUser"
    const val Reffered_User: String = "refferuser"
    const val CONVERSION: String = "conversion"
    const val WALLET_POINT: String = "wallet_point"
    const val LATITUDE: String = "latitude"
    const val LONGITUDE: String = "longitude"

    const val FIRST_TIME: String = "first_time"
    const val IS_LOGIN: String = "isLogin"
    const val PROFILE_DATA: String = "profile_data"

    const val FCM_TOKEN: String = "fcmToken"
    const val DEVICE_ID: String = "deviceId"
    const val FCM_USER_ID: String = "fcmUserid"

    const val FCM_CHAT_ID: String = "fcmChatid"
    const val TOTALAMOUNT: String = "totalAmount"

    private lateinit var preferences: SharedPreferences

    fun clearUserPref() {
        preferences.edit()?.remove(AUTH_TOKEN)?.apply()
        preferences.edit()?.remove(USER_ID)?.apply()
        preferences.edit()?.remove(NAME)?.apply()
        preferences.edit()?.remove(PHONE)?.apply()
        //preferences.edit()?.remove(EMAIL)?.apply()
        //preferences.edit()?.remove(PASSWORD)?.apply()
        preferences.edit()?.remove(GENDER)?.apply()
        preferences.edit()?.remove(COUNTRY_NAME)?.apply()
        preferences.edit()?.remove(COUNTRY_ID)?.apply()
        preferences.edit()?.remove(IMAGE)?.apply()
        preferences.edit()?.remove(ADDRESS)?.apply()
        preferences.edit()?.remove(ROLE)?.apply()
        preferences.edit()?.remove(OTP)?.apply()
        preferences.edit()?.remove(IS_LOGIN)?.apply()
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