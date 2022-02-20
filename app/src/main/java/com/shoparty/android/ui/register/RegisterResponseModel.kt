package com.shoparty.android.ui.register

data class RegisterResponseModel(
    var status: Boolean,
    var message: String,
    var code: Int,
    var data: User
)

class User {

}
