package com.shoparty.android.ui.register

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityRegisterBinding
import com.shoparty.android.ui.activities.mainactivity.MainActivity


import com.shoparty.android.ui.login.LoginActivity

import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import kotlinx.android.synthetic.main.activity_register.*

import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    var cal = Calendar.getInstance()
    private lateinit var viewModel: RegisterViewModel
    private var selecteddate = ""
    private var selectedgender = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProvider(this, ViewModalFactory(application)).get(RegisterViewModel::class.java)
        binding.registerViewModel = viewModel
        initialise()
        setObserver()
    }

    private fun initialise()
    {
        selectedgender=binding.tvMale.text.toString()
        binding.signUpToInBtn.setOnClickListener(this)
        binding.lyFemale.setOnClickListener(this)
        binding.lyMale.setOnClickListener(this)
        binding.ivFemale.setOnClickListener(this)
        binding.ivMale.setOnClickListener(this)
        binding.ivDateOfBirth.setOnClickListener(this)
        binding.signUpBtn.setOnClickListener(this)

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        binding.signUpDobRelativeLay.setOnClickListener {
            DatePickerDialog(
                this@RegisterActivity, R.style.DialogTheme,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.signUp_toIn_btn -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.ly_male -> {
               iv_male.visibility=View.VISIBLE
                iv_female.visibility=View.GONE
                binding.tvMale.setTextColor(Color.parseColor("#E30986"));
                binding.tvFemale.setTextColor(Color.parseColor("#A19989"));
                selectedgender=tv_male.text.toString()

            }
            R.id.ly_female -> {
                iv_male.visibility=View.GONE
                iv_female.visibility=View.VISIBLE
                binding.tvFemale.setTextColor(Color.parseColor("#E30986"));
                binding.tvMale.setTextColor(Color.parseColor("#A19989"));
                selectedgender=tv_female.text.toString()
            }

            binding.signUpBtn.id -> {
                viewModel.postSignUp(selectedgender)
            }
        }
    }


    private fun setObserver() {
        viewModel.signUp.observe(this, { response ->
            when (response)
            {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    PrefManager.write(PrefManager.AUTH_TOKEN, response.data?.token!!)
                    startActivity(Intent(this, LoginActivity::class.java))
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


    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvDateOfBirth!!.text = sdf.format(cal.time)
        selecteddate=sdf.format(cal.time)
    }
}