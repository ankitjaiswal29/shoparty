package com.shoparty.android.ui.register

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityRegisterBinding
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.main.myaccount.termandcondition.TermAndConditionActivity
import com.shoparty.android.ui.verificationotp.VerificationActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

import java.util.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    var cal = Calendar.getInstance()
    private lateinit var viewModel: RegisterViewModel
    private var dob1 = ""
    private var selectedgender = ""
    private var condition_checkable=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            binding.mainLayoutRegister.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }else {
            binding.mainLayoutRegister.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }
        viewModel = ViewModelProvider(
            this,
            ViewModalFactory(application)
        ).get(RegisterViewModel::class.java)
        binding.registerViewModel = viewModel
        initialise()
        setObserver()
    }

    private fun initialise() {
        binding.etCountryCode.isFocusable = false
        selectedgender = binding.tvMale.text.toString()
        binding.tvTermcondition.setOnClickListener(this)
        binding.signUpToInBtn.setOnClickListener(this)
        binding.lyFemale.setOnClickListener(this)
        binding.lyMale.setOnClickListener(this)
        binding.ivFemale.setOnClickListener(this)
        binding.ivMale.setOnClickListener(this)
        binding.ivDateOfBirth.setOnClickListener(this)
        binding.signUpBtn.setOnClickListener(this)
        binding.signUpDobRelativeLay.setOnClickListener {
            val calender = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                R.style.DialogTheme,
                { _, year, monthOfYear, dayOfMonth ->
                    dob1= "$dayOfMonth-${monthOfYear + 1}-$year"
                    var dob= "$year-${monthOfYear + 1}-$dayOfMonth"
                    if(Utils.calculateAgeFromDob(dob,"YYYY-MM-dd")>=18)
                    {
                        binding.tvDateOfBirth.text = dob1
                    }
                    else
                    {
                        dob1=""
                        binding.tvDateOfBirth.text = dob1
                        Utils.showLongToast(this,getString(R.string.agelimitismin))
                    }
                },
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        binding.cbTermconditon.setOnCheckedChangeListener { compoundButton, isChecked ->
            condition_checkable = isChecked

        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.signUp_toIn_btn -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.ly_male -> {
                binding.ivMale.visibility = View.VISIBLE
                binding.ivFemale.visibility = View.GONE
                binding.tvMale.setTextColor(Color.parseColor("#E30986"));
                binding.tvFemale.setTextColor(Color.parseColor("#A19989"));
                selectedgender =  binding.tvMale.text.toString()

            }
            R.id.ly_female -> {
                binding.ivMale.visibility = View.GONE
                binding.ivFemale.visibility = View.VISIBLE
                binding.tvFemale.setTextColor(Color.parseColor("#E30986"));
                binding.tvMale.setTextColor(Color.parseColor("#A19989"))
                selectedgender = binding.tvFemale.text.toString()
            }

            binding.signUpBtn.id -> {
                viewModel.postSignUp(selectedgender,condition_checkable,binding.etCountryCode.text.toString())

            }
            binding.tvTermcondition.id -> {
                val intent = Intent(this, TermAndConditionActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun setObserver() {
        viewModel.signUp.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    PrefManager.write(PrefManager.AUTH_TOKEN, response.data?.token!!)
                    if(intent.extras!=null)
                    {
                        if(intent.getStringExtra(Constants.GUESTUSER)=="1")
                        {
                            val intent = Intent(this, VerificationActivity::class.java)
                            intent.putExtra(Constants.MOBILE, response.data.mobile)
                            intent.putExtra(Constants.USERID, response.data.user_id.toString())
                            intent.putExtra(Constants.OTP, response.data.otp.toString())
                            intent.putExtra(Constants.GUESTUSER, "2")
                            startActivityForResult(intent,Constants.SHOPPINGBAG)
                        }
                        else if(intent.getStringExtra(Constants.GUESTUSER)=="2")
                        {
                            val intent = Intent(this, VerificationActivity::class.java)
                            intent.putExtra(Constants.MOBILE, response.data.mobile)
                            intent.putExtra(Constants.USERID, response.data.user_id.toString())
                            intent.putExtra(Constants.OTP, response.data.otp.toString())
                            intent.putExtra(Constants.GUESTUSER, "2")
                            startActivityForResult(intent,Constants.SHOPPINGBAG)
                        }
                        else
                        {
                            finish()
                        }
                    }
                    else
                    {
                        finish()
                    }
                    PrefManager.write(PrefManager.DOB,binding.tvDateOfBirth.text.toString())
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