package com.shoparty.android.utils.apiutils

class ApiResponse<T>(val status: Int, val message: String)
{
    var data: T? = null
}