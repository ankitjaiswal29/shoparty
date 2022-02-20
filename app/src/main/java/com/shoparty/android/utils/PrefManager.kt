package com.shoparty.android.utils

import android.content.Context


import com.shoparty.android.app.CustomApplication
import android.content.SharedPreferences



object PrefManager {
    private var sharedPreferences: SharedPreferences =
        CustomApplication.application.applicationContext.getSharedPreferences(
            Constants.SHARED_PREF_FILE_NAME,
        Context.MODE_PRIVATE
    )

    // Keys
    const val KEY_AUTH_TOKEN = "Auth_Token"
    const val KEY_EMAIL = "email"
    const val KEY_ID = "id"
    const val KEY_NAME = "name"


    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun getInt(key: String): Int? {
        return sharedPreferences.getInt(key,0)
    }

    fun putString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun putInt(key: String, value: Int?) {
        value?.let { sharedPreferences.edit().putInt(key, it).apply() }
    }


    fun putBoolean(key: String, value: Boolean?) {
        value?.let { sharedPreferences.edit().putBoolean(key, it).apply() }
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun putLong(key : String, value : Long?) {
        value?.let { sharedPreferences.edit().putLong(key, it).apply()}
    }

    fun getLong(key : String) : Long{
        return sharedPreferences.getLong(key, 0)
    }

    fun clearAuthTokenPref(){
        val editor = sharedPreferences.edit()
        editor.remove(KEY_AUTH_TOKEN)
        editor.apply()
    }

    fun clearAllPrefrance(){
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

}