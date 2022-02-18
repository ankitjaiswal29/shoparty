package com.shoparty.android.utils.apiutils



sealed class Resource<T>(
    val message: String? = null,
    val data: T? = null

) {
    class Success<T>(message: String, data: T?=null) : Resource<T>(message, data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(message, data)
    class Loading<T> : Resource<T>()
}
