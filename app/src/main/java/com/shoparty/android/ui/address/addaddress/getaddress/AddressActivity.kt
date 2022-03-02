package com.shoparty.android.ui.address.addaddress.getaddress

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityAddressBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.address.addaddress.addaddress.AddNewAddressActivity
import com.shoparty.android.ui.main.myaccount.MyAccountAdapter
import com.shoparty.android.ui.returnpolicy.ReturnPolicyAdapter
import com.shoparty.android.ui.returnpolicy.ReturnPolicyModel
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.Utils


class AddressActivity : AppCompatActivity(), View.OnClickListener,RecyclerViewClickListener{
    private lateinit var binding: ActivityAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_address)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.text = getString(R.string.addresstitle)
        binding.tvAddnewaddress.setOnClickListener(this)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        val data = ArrayList<ReturnPolicyModel>()
        data.add(ReturnPolicyModel("1","Shipping Details"))
        data.add(ReturnPolicyModel("2","Shipping Details"))
        data.add(ReturnPolicyModel("3","Shipping Details"))

        var  adapter = AddressAdapter(data,this)
        binding.rvAddressrecyclarview.layoutManager = LinearLayoutManager(this)
        binding.rvAddressrecyclarview.adapter = adapter

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



    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.ADDADDRESS_CODE && resultCode == Activity.RESULT_OK)
        {
            Utils.showShortToast(this,"api call")
        }
    }

    override fun click(pos: String) {
        val intent = Intent(this, AddNewAddressActivity::class.java)
        startActivity(intent)
    }
}