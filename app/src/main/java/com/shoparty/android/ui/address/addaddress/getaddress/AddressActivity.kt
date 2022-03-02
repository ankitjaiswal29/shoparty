package com.shoparty.android.ui.address.addaddress.getaddress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityAddressBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.address.addaddress.addaddress.AddNewAddressActivity
import com.shoparty.android.ui.main.myaccount.MyAccountAdapter
import com.shoparty.android.ui.returnpolicy.ReturnPolicyAdapter
import com.shoparty.android.ui.returnpolicy.ReturnPolicyModel


class AddressActivity : AppCompatActivity(), View.OnClickListener,RecyclerViewClickListener{
    private lateinit var binding: ActivityAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_address)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_address)
        initialise()
    }


    private fun initialise() {
        binding.infoTool.tvTitle.setText("Address")
        binding.tvAddnewaddress.setOnClickListener(this)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)

        val data = ArrayList<ReturnPolicyModel>()
        data.add(ReturnPolicyModel("1","Shipping Details"))
        data.add(ReturnPolicyModel("2","Shipping Details"))
        data.add(ReturnPolicyModel("3","Shipping Details"))

       /* val adapter = AddressAdapter(data)
        binding.rvAddressrecyclarview.adapter = adapter

      */
        var  adapter = AddressAdapter(data,this)
        binding.rvAddressrecyclarview.layoutManager = LinearLayoutManager(this)
        binding.rvAddressrecyclarview.adapter = adapter
      //  binding.tvEdit.setOnClickListener(this)

        // binding..setOnClickListener(this)
    }

    override fun onClick(v: View?) {
         when(v?.id){
             R.id.tvAddnewaddress -> {
                 val intent = Intent(this, AddNewAddressActivity::class.java)

                 startActivity(intent)
             }
            /* R.id.tvEdit -> {
                 val intent = Intent(this, AddNewAddressActivity::class.java)
                startActivity(intent)
            }*/
             R.id.iv_drawer_back -> {
                 onBackPressed()
             }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun click(pos: String) {
        val intent = Intent(this, AddNewAddressActivity::class.java)
        startActivity(intent)
    }
}