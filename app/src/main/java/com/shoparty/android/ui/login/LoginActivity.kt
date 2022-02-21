package com.shoparty.android.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityLoginBinding
import com.shoparty.android.ui.verificationotp.VerificationActivity
import com.shoparty.android.ui.register.RegisterActivity
import com.shoparty.android.ui.register.RegisterViewModel
import com.shoparty.android.utils.apiutils.ViewModalFactory
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_login)
        initialise()
       /* viewModel = ViewModelProvider(this, ViewModalFactory(application)).get(LoginViewModel::class.java)
        binding.registerViewModel = viewModel*/
    }


    private fun initialise()
    {
        binding.btnGetOtp.setOnClickListener(this)
        binding.txtSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnGetOtp -> {
                val intent = Intent(this, VerificationActivity::class.java)
                startActivity(intent)
            }
            R.id.txtSignUp -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }
}