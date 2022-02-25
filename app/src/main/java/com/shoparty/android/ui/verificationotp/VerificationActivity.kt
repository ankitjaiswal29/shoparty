package com.shoparty.android.ui.verificationotp

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

import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

import kotlinx.android.synthetic.main.activity_verification.*

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
        userid= intent.getStringExtra(Constants.USERID)!!
        startTimer()

        signin_btn.setOnClickListener {
            Utils.hideKeyboard(this)
            viewModel.postVerifiy(userid)     //api call
        }
        binding.txtotpcount.setOnClickListener {
            Utils.hideKeyboard(this)
            viewModel.postResend(userid)      //api call
            binding.etOtp.setText("")
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
                binding.txtTimecount.visibility = View.INVISIBLE
                binding.txtotpcount.visibility = View.VISIBLE
            }
        }

    private fun startTimer()
    {
        binding.txtTimecount.visibility = View.VISIBLE
        binding.txtotpcount.visibility = View.INVISIBLE
        var timeleft = 2.toDouble().toString()
        binding.txtTimecount.text = timeleft

        countDownTimer.start()
    }

    private fun stopTimer()
    {
        binding.txtTimecount.visibility = View.INVISIBLE
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

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
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

    override fun onStop() {
        super.onStop()
        stopTimer()
    }
}