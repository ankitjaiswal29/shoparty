package com.shoparty.android.utils.apiutils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shoparty.android.ui.login.LoginViewModel
import com.shoparty.android.ui.main.myaccount.MyAccountViewModel
import com.shoparty.android.ui.register.RegisterViewModel
import com.shoparty.android.ui.verificationotp.VerifiyViewModel

class ViewModalFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(app) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(VerifiyViewModel::class.java)) {
            return VerifiyViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(MyAccountViewModel::class.java)) {
            return MyAccountViewModel(app) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}
