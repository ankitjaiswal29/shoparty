package com.shoparty.android.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.shoparty.android.R
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.register.RegisterActivity
import com.shoparty.android.ui.verificationotp.VerificationActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}