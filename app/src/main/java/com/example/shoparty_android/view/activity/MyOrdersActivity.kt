package com.example.shoparty_android.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoparty_android.R
import com.example.shoparty_android.adapter.MyOrderAdapter
import com.example.shoparty_android.databinding.ActivityMyOrdersBinding
import com.example.shoparty_android.databinding.ActivityOrderSuccessfulyBinding

class MyOrdersActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMyOrdersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_my_orders)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_my_orders)
        initialise()


    }

    private fun initialise() {

       // val recyclerview = findViewById<RecyclerView>(R.id.myorder_recyclerview)

        // this creates a vertical layout Manager
        binding.infoTool.tvTitle.setText(getString(R.string.my_orders))
       binding.myorderRecyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<String>()
        data.add("Delivered On Jul 07")
        data.add("Ongoing On Aug 05")
        data.add("Cancelled On Aug 05")
        data.add("Cancelled On Aug 05")
        data.add("Cancelled On Aug 05")
        data.add("Cancelled On Aug 05")
        data.add("Cancelled On Aug 05")
        data.add("Cancelled On Aug 05")


        val adapter = MyOrderAdapter(data)
        binding.myorderRecyclerview.adapter = adapter
    }

    override fun onClick(v: View?) {
        /*when(v?.id){
            R.id.tvViewordertitle -> {
                val intent = Intent(this, OrderDetailsActivity::class.java)
                startActivity(intent)
            }
            R.id.btnSave -> {
                val intent = Intent(this, MyOrdersActivity::class.java)
                startActivity(intent)
            }
        }*/
    }
}