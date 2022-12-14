package com.shoparty.android.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider


import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityLoginBinding
import com.shoparty.android.ui.verificationotp.VerificationActivity
import com.shoparty.android.ui.register.RegisterActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var guestuser: String="1"  //1 for normal user
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_login)
        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            binding.mainLayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }else {
            binding.mainLayout.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }
        viewModel = ViewModelProvider(this, ViewModalFactory(application))[LoginViewModel::class.java]
        binding.loginViewModel = viewModel
        initialise()
        setObserver()
    }
    private fun initialise()
    {
        if(intent.extras!=null)
        {
           guestuser= intent.getStringExtra(Constants.GUESTUSER).toString()
        }
        binding.btnGetOtp.setOnClickListener(this)
        binding.txtSignUp.setOnClickListener(this)
        binding.etCountryCode.isFocusable = false
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.txtSignUp -> {
                val intent = Intent(this, RegisterActivity::class.java)
               // intent.putExtra("mobile",binding.etMobileNo.text.toString().trim())
                startActivity(intent)

            }
            R.id.btnGetOtp->
            {
                viewModel.postLogin(guestuser,binding.etCountryCode.text.toString())
            }
        }
    }

    private fun setObserver()
    {
        viewModel.login.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    PrefManager.write(PrefManager.AUTH_TOKEN, response.data?.token!!)
                 //   PrefManager.write(PrefManager.LANGUAGEID, response.data?.language_id!!)
                    PrefManager.write(PrefManager.GENDER, response.data?.gender.toString())
                    PrefManager.write(PrefManager.IMAGE,response.data?.image.toString())
                    PrefManager.write(PrefManager.MOBILE, response.data?.mobile.toString())
                    PrefManager.write(PrefManager.NAME, response.data?.name.toString())
                    PrefManager.write(PrefManager.EMAIL, response.data?.email.toString())
                    PrefManager.write(PrefManager.DOB, response.data?.dob!!)
                    PrefManager.write(PrefManager.DEVICETOKEN, response.data?.device_token!!)

                    if(response.data.completely_registered == "1")  // if normal user mobile number ---- land on verification otp page
                    {

                        if(intent.extras!=null)
                        {
                            if(intent.getStringExtra(Constants.GUESTUSER).equals("2"))
                            {
                                val intent = Intent(this, VerificationActivity::class.java)
                                intent.putExtra(Constants.MOBILE, response.data.mobile)
                                intent.putExtra(Constants.USERID, response.data.user_id.toString())
                                intent.putExtra(Constants.OTP, response.data.otp.toString())
                                intent.putExtra(Constants.GUESTUSER, "2")
                                startActivityForResult(intent,Constants.SHOPPINGBAG)
                            }
                            else if(intent.getStringExtra(Constants.GUESTUSER).equals("1"))
                            {
                                val intent = Intent(this, VerificationActivity::class.java)
                                intent.putExtra(Constants.MOBILE, response.data.mobile)
                                intent.putExtra(Constants.USERID, response.data.user_id.toString())
                                intent.putExtra(Constants.OTP, response.data.otp.toString())
                                intent.putExtra(Constants.GUESTUSER, "1")
                                startActivityForResult(intent,Constants.SHOPPINGBAG)
                            }

                        }
                        else  //normal user
                        {
                            val intent = Intent(this, VerificationActivity::class.java)
                            intent.putExtra(Constants.MOBILE, response.data.mobile)
                            intent.putExtra(Constants.USERID, response.data.user_id.toString())
                            intent.putExtra(Constants.OTP, response.data.otp.toString())
                            startActivity(intent)
                        }
                    }
                    else  //else --- land on Signup page
                    {
                        val intent = Intent(this, RegisterActivity::class.java)
                        intent.putExtra(Constants.GUESTUSER,guestuser)
                        startActivityForResult(intent,Constants.SHOPPINGBAG)
                    }

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
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.SHOPPINGBAG && resultCode == Activity.RESULT_OK)
        {
            setResult(Activity.RESULT_OK)
            finish()
        }

    }
}