package com.shoparty.android.ui.myorders.myorder


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityMyOrdersBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.cancelorder.cancelorder.CancelOrderActivity
import com.shoparty.android.ui.myorders.orderdetails.OrderDetailsActivity
import com.shoparty.android.ui.ongoingorder.OngoingOrderActivity

class MyOrdersActivity : AppCompatActivity(), View.OnClickListener, RecyclerViewClickListener {
    private lateinit var binding: ActivityMyOrdersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_orders)
        initialise()
    }

    private fun initialise() {
        // this creates a vertical layout Manager
        binding.infoTool.tvTitle.setText(getString(R.string.my_orders))
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.myorderRecyclerview.layoutManager = LinearLayoutManager(this)
        myOrderListing()

    }

    private fun myOrderListing() {
        val data = ArrayList<String>()
        data.add("Delivered On Jul 07")
        data.add("Ongoing On Aug 05")
        data.add("Cancelled On Aug 05")
        data.add("Cancelled On Aug 05")
        data.add("Cancelled On Aug 05")
        data.add("Cancelled On Aug 05")
        data.add("Cancelled On Aug 05")
        data.add("Cancelled On Aug 05")


        val adapter = MyOrderAdapter(data, this)
        binding.myorderRecyclerview.adapter = adapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_drawer_back -> {
                onBackPressed()
            }

        }
    }

    override fun click(pos: String) {
        when (pos) {
            "Delivered On Jul 07" -> {
                var intent = Intent(this, OrderDetailsActivity::class.java)
                intent.putExtra("data", "myorder")
                startActivity(intent)
                /*  Intent intent =  Intent(context, DestinationActivityName.class);
                  intent.putExtra(Key, Value);
                  startActivity(intent)*/
            }
            "Ongoing On Aug 05" -> startActivity(Intent(this, OngoingOrderActivity::class.java))
            "Cancelled On Aug 05" -> {
                val intent = Intent(this, CancelOrderActivity::class.java)
                intent.putExtra("key", "Myorder")
                startActivity(intent)
            } //startActivity(Intent(this, CancelOrderActivity::class.java))

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}