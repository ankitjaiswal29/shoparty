package com.example.shoparty_android.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoparty_android.R
import com.example.shoparty_android.`interface`.RecyclerViewClickListener
import com.example.shoparty_android.adapter.MyAccountAdapter
import com.example.shoparty_android.model.MyAccountModel

class MyAccountActivity : AppCompatActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<MyAccountModel>()
        data.add(MyAccountModel(R.drawable.ic_myorder_icon,"idmyorder",getString(R.string.my_order) ))
        data.add(MyAccountModel(R.drawable.ic_vouchers_icon,"idvoucher",getString(R.string.vouchers) ))

        data.add(MyAccountModel(R.drawable.ic_wishlist_icon,"idwishlist",getString(R.string.wishlist) ))
        data.add(MyAccountModel(R.drawable.ic_myprofile_icon,"idmyprofile",getString(R.string.my_profile) ))

        data.add(MyAccountModel(R.drawable.ic_address_icon,"idaddress",getString(R.string.address_book) ))
        data.add(MyAccountModel(R.drawable.ic_rate_our_icon,"idrate",getString(R.string.rate_our_app) ))


        data.add(MyAccountModel(R.drawable.ic_contact_icon,"idcontact",getString(R.string.contact_us) ))
        data.add(MyAccountModel(R.drawable.ic_aboutus_icon,"idabout",getString(R.string.about_us) ))

        data.add(MyAccountModel(R.drawable.ic_term_and_conditon_icon,"idtermcondition",getString(R.string.terms_and_conditions) ))
        data.add(MyAccountModel(R.drawable.ic_privacy_policy_icon,"iprivacypolicy",getString(R.string.privacy_policy) ))

        data.add(MyAccountModel(R.drawable.ic_return_policy_icon,"idreturnpolicy",getString(R.string.return_policy) ))
        data.add(MyAccountModel(R.drawable.ic_sign_out_icon,"idsignout",getString(R.string.sign_out) ))


        val adapter = MyAccountAdapter(data,)
        recyclerview.adapter = adapter
    }

   /* override fun itemclick(position: String) {
        
    }*/


}