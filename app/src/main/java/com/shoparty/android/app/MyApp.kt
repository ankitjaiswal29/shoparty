package com.shoparty.android.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.shoparty.android.utils.PrefManager

class MyApp : Application() {
    companion object {

        lateinit var application: Application

        fun getInstance(): Application {
            return application
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        PrefManager.init(applicationContext)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        // AppDatabase.invoke(applicationContext)
    }
}