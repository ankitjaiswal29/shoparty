package com.shoparty.android.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityWishListBinding
import com.shoparty.android.ui.adapters.WishListAdapter


class WishListActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityWishListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_wish_list)
        binding= DataBindingUtil.setContentView(this,
            R.layout.activity_wish_list)
        initialise()
    }

    private fun initialise() {

        // val recyclerview = findViewById<RecyclerView>(R.id.myorder_recyclerview)

        // this creates a vertical layout Manager
        binding.infoTool.tvTitle.setText(getString(R.string.wishlist))
        binding.infoTool.ivBag.visibility=View.VISIBLE
        binding.infoTool.ivSearch.visibility=View.VISIBLE
        binding.vouchersRecyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<String>()
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")




        val adapter = WishListAdapter(data)
        binding.vouchersRecyclerview.adapter = adapter
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