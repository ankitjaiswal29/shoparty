package com.shoparty.android.ui.verificationotp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityVerificationBinding
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.main.myaccount.getprofile.GetProfileResponse
import com.shoparty.android.utils.*

import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

import kotlinx.android.synthetic.main.activity_verification.*
import java.util.*

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
    private lateinit var viewModel: VerifiyViewModel
    private var userid=""
    private var clickabletrue=""
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
      //  binding.editTextNumberPassword5.addTextChangedListener(OtpTextWatcher(binding.editTextNumberPassword5, binding.editTextNumberPassword4))
        Utils.showShortToast(this,intent.getStringExtra(Constants.OTP))
        userid= intent.getStringExtra(Constants.USERID)!!
        startTimer()

        signin_btn.setOnClickListener {
            Utils.hideKeyboard(this)
            viewModel.postVerifiy(userid)     //api call
        }
        binding.txtotpcount.setOnClickListener {
            Utils.hideKeyboard(this)
            viewModel.postResend(userid)      //api call
        }

    }

    private fun verificationSuccessDialog() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialogWithMargin)
        val inflater = layoutInflater
        val dialogLayout: View =
            inflater.inflate(R.layout.verification_dialog_layout, null)
        val btn_done = dialogLayout.findViewById<Button>(R.id.btnDone)
          builder.setView(dialogLayout)
        val builderinstance= builder.show()
        btn_done.setOnClickListener {
            builder.setCancelable(true)
            Toast.makeText(this, "done", Toast.LENGTH_LONG).show()
            builderinstance.dismiss()
        }


    }

    private var countDownTimer =
        object : CountDownTimer(60000 * 2, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val elapsedFormattedString = seconds.toString()
                binding.txtTimecount.text = elapsedFormattedString+" "+getString(R.string.txtseconds)
            }

            override fun onFinish()
            {
                binding.txtTimecount.setClickable(true)
                clickabletrue="true"
            }
        }

    private fun startTimer()
    {
        clickabletrue="false"
        var timeleft = 2.toDouble().toString()
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
        viewModel.verifiyotp.observe(this, { response ->
            when (response)
            {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    setupUI(response.data?.data)
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

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
        })


        viewModel.resendOtp.observe(this, { response ->
            when (response)
            {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
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
        })
    }
    private fun setupUI(data: VerifiyOtpResponse.Data?)
    {

        PrefManager.write(PrefManager.IMAGE,data?.image.toString())
        PrefManager.write(PrefManager.MOBILE, data?.mobile.toString())
        PrefManager.write(PrefManager.NAME, data?.name.toString())

    }

    override fun onStop() {
        super.onStop()
        stopTimer()
    }
}