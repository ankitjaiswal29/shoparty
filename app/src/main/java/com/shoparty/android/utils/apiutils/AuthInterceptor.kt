package com.shoparty.android.utils.apiutils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.shoparty.android.utils.PrefManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isConnected()) {
            throw NoConnectivityException()
        }
        val requestBuilder = chain.request().newBuilder()
        val authToken = PrefManager.getString(PrefManager.KEY_AUTH_TOKEN)
        Log.e("AuthToken", authToken!!)
        requestBuilder.addHeader("Authorization", authToken)
        return chain.proceed(requestBuilder.build())
    }

    private fun isConnected(): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No Internet Connection"
}