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
import com.shoparty.android.ui.address.addaddress.getaddress.GetAddressListResponse
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
class AddNewAddressActivity : AppCompatActivity(), View.OnClickListener {
    private var selectedcountryid = ""
    private var updatepagestatus = ""
    private lateinit var binding: ActivityAddNewAddressBinding
    private lateinit var viewModel: AddressViewModel
    private var countrylist: ArrayList<String> = ArrayList()
    private var countryidlist: ArrayList<String> = ArrayList()
    private var citylist: java.util.ArrayList<String> = java.util.ArrayList()
    private var cityidlist: java.util.ArrayList<String> = java.util.ArrayList()
    private var selectedcityid = ""
    private var addressid = ""
    private var cityid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_address)
        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            binding.mainLayoutAddress.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.infoTool.ivDrawerBack.rotation = 0F
        }else {
            binding.mainLayoutAddress.layoutDirection = View.LAYOUT_DIRECTION_LTR
            binding.infoTool.ivDrawerBack.rotation = 180F
        }
        viewModel =
            ViewModelProvider(this, ViewModalFactory(application))[AddressViewModel::class.java]
        binding.addressViewModel = viewModel
        initialise()
        viewModel.getcountrylist()      //api call
        setObserver()
    }

    private fun initialise() {
        if (intent.extras != null) {
            val addressData = intent.getParcelableExtra<GetAddressListResponse.AddressData>(Constants.ADDRESSSDATA)!!
            updatepagestatus = intent.getStringExtra(Constants.PAGESTATUS)!!
            addressid = addressData.address_id.toString()
            viewModel.etFirstname.set(addressData.first_name)
            viewModel.etLasttName.set(addressData.last_name)
            viewModel.etStreatLandmark.set(addressData.street_no)
            viewModel.etBuildingnoApartment.set(addressData.building_no)
            viewModel.etMobile.set(addressData.mobile)
            binding.infoTool.tvTitle.text = getString(R.string.editaddress)
            cityid = addressData.city_id
        }
        else {
            binding.infoTool.tvTitle.text = getString(R.string.add_new_address)
        }
        binding.etCountryCode.setText(PrefManager.read(PrefManager.COUNTRYCODE,""))
        binding.etCountryCode.isFocusable = false
        binding.btnCan.setOnClickListener(this)
        binding.btnSav.setOnClickListener(this)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_can -> {
                finish()
            }
            R.id.btn_sav -> {
                if (updatepagestatus == "1")
                {
                    viewModel.updateAddress(
                        selectedcountryid,
                        selectedcityid,
                        addressid)   //api call
                }
                else
                {
                    viewModel.addAddress(selectedcountryid, selectedcityid,binding.etCountryCode.text.toString())   //api call
                }

            }
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun setObserver() {
        viewModel.getcountry.observe(this) { response ->
            when (response) {
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
        }


        viewModel.getcity.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    citylist.clear()
                    cityidlist.clear()
                    response.data?.forEachIndexed { index, data ->
                        citylist.add(response.data[index].city_name)
                        cityidlist.add(response.data[index].city_id.toString())
                    }
                    setupCityData(citylist)
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

        viewModel.address.observe(this) { response ->
            when (response) {
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
        }


        viewModel.updateaddress.observe(this) { response ->
            when (response) {
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
        }
    }

    private fun setupCountryData(data: ArrayList<String>) {
        val arrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_item_custom, data)
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom)
        binding.spLasttName.adapter = arrayAdapter

        binding.spLasttName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                selectedcountryid = countryidlist[position]
                viewModel.getcitylist(selectedcountryid)      //api call
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    private fun setupCityData(data: java.util.ArrayList<String>) {
        val arrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_item_custom, data)
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom)
        binding.etCity.adapter = arrayAdapter

        cityidlist.forEachIndexed { index, s ->
            if (cityid == s) {
                binding.etCity.setSelection(index)
            }

            binding.etCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    selectedcityid = cityidlist[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }
}
