package com.shoparty.android.ui.address.addaddress.getaddress

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityAddressBinding
import com.shoparty.android.interfaces.RecyclerViewAddressClickListener
import com.shoparty.android.ui.address.addaddress.AddressViewModel
import com.shoparty.android.ui.address.addaddress.addaddress.AddNewAddressActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.ProgressDialog.hideProgressBar
import com.shoparty.android.utils.ProgressDialog.showProgressBar
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

class AddressActivity : AppCompatActivity(), View.OnClickListener,
    RecyclerViewAddressClickListener {
    private lateinit var adapter: AddressAdapter
    private lateinit var binding: ActivityAddressBinding
    private lateinit var viewModel: AddressViewModel
    private var addresslist: ArrayList<GetAddressListResponse.AddressData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_address)

        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            binding.mainLayoutAddressd.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.infoTool.ivDrawerBack.rotation = 0F
        }else {
            binding.mainLayoutAddressd.layoutDirection = View.LAYOUT_DIRECTION_LTR
            binding.infoTool.ivDrawerBack.rotation = 180F
        }

        viewModel = ViewModelProvider(this, ViewModalFactory(application))[AddressViewModel::class.java]
        initialise()
        viewModel.getaddresslist()    //api call
        setObserver()
    }

    private fun initialise()
    {
        binding.infoTool.tvTitle.text = getString(R.string.addresstitle)
        binding.tvAddnewaddress.setOnClickListener(this)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
         when(v?.id){
             R.id.tvAddnewaddress -> {
                 val intent = Intent(this, AddNewAddressActivity::class.java)
                 startActivityForResult(intent, Constants.ADDADDRESS_CODE)
             }
             R.id.iv_drawer_back -> {
                 onBackPressed()
             }
        }
    }

    private fun setObserver() {

        viewModel.getaddress.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()

                    addresslist.clear()
                    addresslist = response.data as ArrayList<GetAddressListResponse.AddressData>
                    setAddressListAdapter(addresslist)
                }
                is Resource.Loading -> {
                    showProgressBar(this)
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.deleteaddress.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    Toast.makeText(applicationContext, response.message, Toast.LENGTH_SHORT).show() //  viewModel.getaddresslist()  //api call
                }
                is Resource.Loading -> {
                    showProgressBar(this)
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setAddressListAdapter(data: List<GetAddressListResponse.AddressData>?)
    {
        adapter = AddressAdapter(data!!,this)
        binding.rvAddressrecyclarview.layoutManager = LinearLayoutManager(this)
        binding.rvAddressrecyclarview.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.ADDADDRESS_CODE && resultCode == Activity.RESULT_OK)
        {
               viewModel.getaddresslist()    //api call
        }
    }

    override fun editclick(address_id: Int, addressAddressData: GetAddressListResponse.AddressData) {
        val intent = Intent(this, AddNewAddressActivity::class.java)
        intent.putExtra(Constants.ADDRESSSDATA,addressAddressData)
        intent.putExtra(Constants.PAGESTATUS,Constants.UPDATEADDRESSSTATUS)
        startActivityForResult(intent, Constants.ADDADDRESS_CODE)
    }

    override fun removeclick(address_id: Int, position: Int) {
        viewModel.deleteAddress(address_id)
        addresslist.removeAt(position)
        adapter.notifyDataSetChanged()
    }

    override fun addressitemclick(address_id: Int, fulladdress: String) {
        if(intent.extras!=null)
        {
            val intent = Intent()
            intent.putExtra("fulladdress",fulladdress)
            intent.putExtra("addressid", address_id.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}