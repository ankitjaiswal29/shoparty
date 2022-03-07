package com.shoparty.android.network

import android.annotation.SuppressLint
import android.content.Context
import androidx.viewbinding.BuildConfig

import com.google.gson.GsonBuilder
import com.shoparty.android.app.MyApp
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.apiutils.AuthInterceptor

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.util.concurrent.TimeUnit

@SuppressLint("StaticFieldLeak")
object RetrofitBuilder {
    private lateinit var retrofit: Retrofit

    private fun getRetrofitInstance(): Retrofit {
        val context: Context = MyApp.getInstance().applicationContext
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            // development build
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            // production build
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        val authInterceptor = AuthInterceptor(context)
     //   val authToken = PrefManager.read(PrefManager.AUTH_TOKEN,"")
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
           /* .addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization","Bearer $authToken").build()
                chain.proceed(request) }*/
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        if (!(::retrofit.isInitialized)) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit
    }

    val apiService: ApiService? = getRetrofitInstance().create(ApiService::class.java)

}