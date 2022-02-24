package com.shoparty.android.ui.myaccount

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R

import com.shoparty.android.databinding.ActivityMyProfileBinding
import java.text.SimpleDateFormat
import java.util.*


class MyProfileActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMyProfileBinding
    var cal = Calendar.getInstance()
    private var selecteddate = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_my_profile)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_my_profile)
        initialise()
    }

    private fun initialise() {
        binding.btnSave.setOnClickListener(this)
        binding.tvDateBirth.setOnClickListener(this)
        binding.infoTool.back.visibility=View.VISIBLE
        binding.infoTool.tvTitle.setText(getString(R.string.my_account))
        binding.infoTool.back.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSave -> {
                /*val intent = Intent(this, MyAccountActivity::class.java)
                startActivity(intent)*/
                finish()
            }
            R.id.back -> {
                onBackPressed()

            }
            R.id.tvDateBirth -> {
                DatePic()

            }
        }
    }

    private fun DatePic() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }


            DatePickerDialog(
                this, R.style.DialogTheme,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()


    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvDateBirth!!.text = sdf.format(cal.time)
        selecteddate=sdf.format(cal.time)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}