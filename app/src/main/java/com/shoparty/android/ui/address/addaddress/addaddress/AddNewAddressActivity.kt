package com.shoparty.android.ui.address.addaddress.addaddress

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityAddNewAddressBinding
import com.shoparty.android.ui.address.addaddress.AddressViewModel
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
class AddNewAddressActivity : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {
    private var selectedcountryid=""
    private lateinit var binding: ActivityAddNewAddressBinding
    private lateinit var viewModel: AddressViewModel
    private var countrylist: ArrayList<String> = ArrayList()
    private var countryidlist: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_add_new_address)
        viewModel = ViewModelProvider(this, ViewModalFactory(application))[AddressViewModel::class.java]
        binding.addressViewModel = viewModel
        initialise()
        viewModel.getcountrylist()      //api call
        setObserver()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.text = getString(R.string.add_new_address)
        binding.btnCan.setOnClickListener(this)
        binding.btnSav.setOnClickListener(this)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_can -> {
                finish()
            }
            R.id.btn_sav -> {
               viewModel.postaddAddress(selectedcountryid)   //api call
            }
            R.id.iv_drawer_back -> {
             onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun setObserver()
    {
        viewModel.getcountry.observe(this, { response ->
            when (response)
            {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    countrylist.clear()
                    countryidlist.clear()
                    response.data?.forEachIndexed { index, data ->
                        countrylist.add(response.data[index].country_name)
                        countryidlist.add(response.data[index].country_id.toString())
                    }
                    setupCountryData(countrylist)
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

        viewModel.address.observe(this, { response ->
            when (response)
            {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    setResult(Activity.RESULT_OK, intent)
                    finish()
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

    private fun setupCountryData(data: ArrayList<String>)
    {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spLasttName.adapter = arrayAdapter
        binding.spLasttName.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedcountryid=countryidlist[position]
       // Utils.showShortToast(this,selectedcountryid)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


}