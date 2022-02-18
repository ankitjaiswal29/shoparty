package com.shoparty.android.ui.register

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivitySignUpBinding


import com.shoparty.android.ui.mainactivity.MainActivity
import com.shoparty.android.ui.login.SignInActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.apiutils.Resource

import kotlinx.android.synthetic.main.activity_sign_up.*
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignUpBinding
    var cal = Calendar.getInstance()
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        initialise()
        setObserver()
    }

    private fun initialise() {
        binding.signUpToInBtn.setOnClickListener(this)
        binding.lyFemale.setOnClickListener(this)
        binding.lyMale.setOnClickListener(this)
        binding.ivFemale.setOnClickListener(this)
        binding.ivMale.setOnClickListener(this)
        binding.ivDateOfBirth.setOnClickListener(this)
        binding.signUpBtn.setOnClickListener(this)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        binding.ivDateOfBirth.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                DatePickerDialog(this@SignUpActivity,R.style.DialogTheme,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

    }



    override fun onClick(v: View?) {
        when(v?.id){
            R.id.signUp_toIn_btn -> {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
            R.id.ly_male -> {
               iv_male.visibility=View.VISIBLE
                iv_female.visibility=View.GONE
                binding.tvMale.setTextColor(Color.parseColor("#E30986"));
                binding.tvFemale.setTextColor(Color.parseColor("#A19989"));

            }
            R.id.ly_female -> {
                iv_male.visibility=View.GONE
                iv_female.visibility=View.VISIBLE
                binding.tvFemale.setTextColor(Color.parseColor("#E30986"));
                binding.tvMale.setTextColor(Color.parseColor("#A19989"));
            }

            binding.signUpBtn.id -> {
               // registerUser()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }


        }



    }


    private fun registerUser() {
        /*if (binding.etFirstName.text.isNullOrBlank()) {
            Toast.makeText(this@RegistrationActivity, "Enter First Name", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.etLastName.text.isNullOrBlank()) {
            Toast.makeText(this@RegistrationActivity, "Enter Last Name", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.etMobileNumber.text.isNullOrBlank()) {
            Toast.makeText(this@RegistrationActivity, "Enter Number", Toast.LENGTH_SHORT).show()
            return
        }
        if(!checkValidMobile(binding.etMobileNumber.text.toString())){
            Toast.makeText(this@RegistrationActivity, "Enter Number Valid Mobile Number", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.etEmail.text.isNullOrBlank()) {
            Toast.makeText(this@RegistrationActivity, "Enter Email", Toast.LENGTH_SHORT).show()
            return
        }
        if(!Utils.isValidEmail(binding.etEmail.text.toString())){
            Toast.makeText(this@RegistrationActivity, "Please Valid Enter Email", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.etDob.text.isNullOrBlank()) {
            Toast.makeText(this@RegistrationActivity, "Enter Date of Birth", Toast.LENGTH_SHORT).show()
            return
        }*/

        val request = RegisterRequestModel(
            Constants.DEVICE_TYPE,
            binding.signUpNameEdTxt.text.toString())
            viewModel.postSignUp(request)
    }

    private fun setObserver() {
        viewModel.signUp.observe(this, { response ->
            when (response) {
                is Resource.Success -> {
                   /*ProgressDialog.hideProgressBar()
                    PrefManager.write(PrefManager.AUTH_TOKEN, response.data?.token!!)
                    PrefManager.write(PrefManager.COUNTRY_ID, binding.ccp.selectedCountryCodeWithPlus)
                    PrefManager.write(PrefManager.PHONE, binding.etMobileNumber.text.toString())
                    PrefManager.write(PrefManager.OTP, response.data.otp.toString())
                    startActivity(Intent(this@RegistrationActivity, OTPActivity::class.java))*/
                }
                is Resource.Loading -> {
                  //  ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                 //   ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                 //   ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }


    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvDateOfBirth!!.text = sdf.format(cal.getTime())
    }
}