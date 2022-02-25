package com.shoparty.android.ui.myaccount

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R

import com.shoparty.android.databinding.ActivityMyProfileBinding
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.tv_female
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
        binding.tvMale.setOnClickListener(this)
        binding.tvFemale.setOnClickListener(this)
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
            R.id.tvMale -> {
               // iv_male.visibility=View.VISIBLE
                //iv_female.visibility=View.GONE
                binding.tvMale.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pink_check, 0, 0, 0);
                binding.tvFemale.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                binding.tvFemale.setTextColor(Color.parseColor("#E30986"));
                binding.tvMale.setTextColor(Color.parseColor("#E30986"));
                binding.tvFemale.setTextColor(Color.parseColor("#A19989"));
               // selectedgender=tv_male.text.toString()

            }
            R.id.tv_female -> {
              //  iv_male.visibility=View.GONE
                //iv_female.visibility=View.VISIBLE
               // tv_female.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pink_check, 0, 0, 0);
                //tv_male.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding.tvMale.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                binding.tvFemale.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pink_check, 0, 0, 0);

                binding.tvFemale.setTextColor(Color.parseColor("#E30986"));
                binding.tvMale.setTextColor(Color.parseColor("#A19989"));
                //selectedgender=tv_female.text.toString()
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