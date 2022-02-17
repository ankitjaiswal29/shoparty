package com.shoparty.android.ui.activities.register

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R

import com.shoparty.android.databinding.ActivitySignUpBinding
import com.shoparty.android.ui.activities.mainactivity.MainActivity
import com.shoparty.android.ui.activities.login.SignInActivity

import kotlinx.android.synthetic.main.activity_sign_up.*
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignUpBinding
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        initialise()

        binding.signUpBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initialise() {
        binding.signUpToInBtn.setOnClickListener(this)
        binding.lyFemale.setOnClickListener(this)
        binding.lyMale.setOnClickListener(this)
        binding.ivFemale.setOnClickListener(this)
        binding.ivMale.setOnClickListener(this)
        binding.ivDateOfBirth.setOnClickListener(this)

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
                    // set DatePickerDialog to point to today's date when it loads up
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


        }



    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvDateOfBirth!!.text = sdf.format(cal.getTime())
    }
}