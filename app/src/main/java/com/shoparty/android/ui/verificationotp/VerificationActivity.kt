package com.shoparty.android.ui.verificationotp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityVerificationBinding
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.utils.*

import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

import kotlinx.android.synthetic.main.activity_verification.*
import java.util.*

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
    private lateinit var viewModel: VerifiyViewModel
    private var userid=""
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_verification)
        viewModel = ViewModelProvider(this, ViewModalFactory(application))[VerifiyViewModel::class.java]
        binding.verifiyViewModel = viewModel
        initialise()
        setObserver()
    }

    private fun initialise()
    {
        binding.tvMobileno.text= intent.getStringExtra(Constants.MOBILE)

        binding.editTextNumberPassword.addTextChangedListener(OtpTextWatcher(binding.editTextNumberPassword2, binding.editTextNumberPassword))
        binding.editTextNumberPassword2.addTextChangedListener(OtpTextWatcher(binding.editTextNumberPassword3, binding.editTextNumberPassword))
        binding.editTextNumberPassword3.addTextChangedListener(OtpTextWatcher(binding.editTextNumberPassword4, binding.editTextNumberPassword2))
        binding.editTextNumberPassword4.addTextChangedListener(OtpTextWatcher(binding.editTextNumberPassword4, binding.editTextNumberPassword3))
        Utils.showShortToast(this,intent.getStringExtra(Constants.OTP))
        userid= intent.getStringExtra(Constants.USERID)!!
        startTimer()

        signin_btn.setOnClickListener {
            Utils.hideKeyboard(this)
            viewModel.postVerifiy(userid)     //api call
        }
        binding.txtotpcountvisible.setOnClickListener {
            Utils.hideKeyboard(this)
            viewModel.postResend(userid)      //api call
        }

    }

    private var countDownTimer =
        object : CountDownTimer((60000 * 2).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long)
            {
                val seconds = millisUntilFinished / 1000
                val elapsedFormattedString = seconds.toString()
                binding.txtTimecount.text = elapsedFormattedString+" "+getString(R.string.txtseconds)
            }
            override fun onFinish()
            {
                txtotpcountvisible.visibility=View.VISIBLE
                txtotpcount.visibility=View.GONE
                txtTimecount.visibility=View.GONE
            }
        }

    private fun startTimer()
    {
        txtotpcountvisible.visibility=View.GONE
        txtotpcount.visibility=View.VISIBLE
        txtTimecount.visibility=View.VISIBLE

        val timeleft = 2.toDouble().toString()
        binding.txtTimecount.text = timeleft

        countDownTimer.start()
    }

    private fun stopTimer()
    {
        binding.txtTimecount.visibility = View.GONE
        binding.txtotpcount.visibility = View.VISIBLE
        countDownTimer?.cancel()
    }

    private fun setObserver()
    {
        viewModel.verifiyotp.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    setupUI(response)
                    Toast.makeText(applicationContext,getString(R.string.loginsuccess) ,Toast.LENGTH_SHORT).show()
                    if(intent.extras!=null)
                    {
                        if(intent.getStringExtra(Constants.GUESTUSER).equals("2")) //guest user
                        {
                             setResult(Activity.RESULT_OK,intent)
                             finish()
                        }
                        else if(intent.getStringExtra(Constants.GUESTUSER).equals("1")) //without guest but without login user
                        {
                            setResult(Activity.RESULT_OK,intent)
                            finish()
                        }
                        else                     //normal user
                        {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    else      //normal user
                    {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext, response.message, Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        viewModel.resendOtp.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()

                    Toast.makeText(
                        applicationContext, response.message, Toast.LENGTH_LONG
                    ).show()

                    startTimer()
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    private fun setupUI(data: Resource<VerifiyOtpResponse>)
    {
        PrefManager.write(PrefManager.IMAGE, data.data?.result?.data!!.image)
        PrefManager.write(PrefManager.MOBILE, data.data?.result?.data!!.mobile.toString())
        PrefManager.write(PrefManager.NAME, data.data?.result?.data!!.name.toString())
        PrefManager.write(PrefManager.USER_ID, data.data?.result?.data!!.user_id.toString())
        PrefManager.write(PrefManager.EMAIL, data.data?.result?.data!!.toString())
        PrefManager.write(PrefManager.USER_ID, data.data?.result?.data!!.toString())
        PrefManager.write(PrefManager.AUTH_TOKEN, data.data.token)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

}