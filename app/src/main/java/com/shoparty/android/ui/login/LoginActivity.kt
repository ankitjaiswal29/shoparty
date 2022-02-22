package com.shoparty.android.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityLoginBinding
import com.shoparty.android.ui.activities.mainactivity.MainActivity
import com.shoparty.android.ui.verificationotp.VerificationActivity
import com.shoparty.android.ui.register.RegisterActivity
import com.shoparty.android.ui.register.RegisterViewModel
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this, ViewModalFactory(application))[LoginViewModel::class.java]
        binding.loginViewModel = viewModel
        initialise()
        setObserver()
    }

    private fun initialise()
    {
        binding.btnGetOtp.setOnClickListener(this)
        binding.txtSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnGetOtp -> {
                viewModel.postLogin()  //api call
            }
            R.id.txtSignUp -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setObserver()
    {
        viewModel.login.observe(this, { response ->
            when (response)
            {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    PrefManager.write(PrefManager.AUTH_TOKEN, response.data?.token!!)
                    val intent = Intent(this, VerificationActivity::class.java)
                    intent.putExtra(Constants.MOBILE,response.data.mobile)
                    startActivity(intent)

                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    com.shoparty.android.utils.ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}