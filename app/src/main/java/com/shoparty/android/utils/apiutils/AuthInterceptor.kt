package com.shoparty.android.utils.apiutils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.shoparty.android.utils.PrefManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor (val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isConnected()) {
            throw NoConnectivityException()
        }
        val requestBuilder = chain.request().newBuilder()
        val authToken = PrefManager.read(PrefManager.AUTH_TOKEN,"")
        Log.e("AuthToken", authToken!!)
        requestBuilder.addHeader("Authorization","Bearer $authToken")
        return try {
            chain.proceed(requestBuilder.build())
        } catch (e:Exception) {
            chain.proceed(chain.request().newBuilder().build())
        }

    }

    private fun isConnected(): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}

class NoConnectivityException : IOException() {
    // You can send any message whatever you want from here.
    override val message: String
        get() = "No Internet Connection"
    // You can send any message whatever you want from here.
}