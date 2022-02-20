package com.shoparty.android.network

import android.annotation.SuppressLint
import android.content.Context

import com.google.gson.GsonBuilder
import com.shoparty.android.app.CustomApplication
import com.shoparty.android.utils.apiutils.AuthInterceptor
import com.shoparty.android.utils.apiutils.ConstantsApi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

@SuppressLint("StaticFieldLeak")
object RetrofitBuilder {
    private const val BASE_URL: String = ConstantsApi.SERVER_URL
    val context: Context = CustomApplication.getInstance().applicationContext

    private fun getRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val authInterceptor = AuthInterceptor(context)
        val gson = GsonBuilder().setLenient().create()

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }
    var apiService: ApiService = getRetrofit().create(ApiService::class.java)

   // var apiImageService: ApiService = getImageRetrofit().create(ApiService::class.java)

}