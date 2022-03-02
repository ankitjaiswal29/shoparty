package com.shoparty.android.ui.wishlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityWishListBinding
import com.shoparty.android.ui.main.wishlist.WishListAdapter
import com.shoparty.android.ui.search.SearchActivity
import com.shoparty.android.ui.shipping.ShippingActivity


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
        binding.infoTool.ivBagBtn.visibility=View.VISIBLE
        binding.infoTool.ivBtnsearch.visibility=View.VISIBLE
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.infoTool.ivBagBtn.setOnClickListener(this)
        binding.infoTool.ivBtnsearch.setOnClickListener(this)
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
        when(v?.id){
            R.id.iv_drawer_back -> {
               onBackPressed()
            }
            R.id.ivBagBtn -> {
                val intent = Intent(this, ShippingActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_btnsearch -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}