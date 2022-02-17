package com.shoparty.android.ui.activities.vouchers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityVouchersBinding
import com.shoparty.android.ui.adapters.VouchersAdapter


class VouchersActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityVouchersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_vouchers)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_vouchers)
        initialise()
    }

    private fun initialise() {

        // val recyclerview = findViewById<RecyclerView>(R.id.myorder_recyclerview)

        // this creates a vertical layout Manager
        binding.infoTool.tvTitle.setText(getString(R.string.vouchers))
        binding.infoTool.back.setOnClickListener(this)
        binding.vouchersRecyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<String>()
        data.add("On Minimum Purchase Of $. 200")
        data.add("On Minimum Purchase Of $. 200")
        data.add("On Minimum Purchase Of $. 200")
        data.add("On Minimum Purchase Of $. 200")
        data.add("On Minimum Purchase Of $. 200")
        data.add("On Minimum Purchase Of $. 200")
        data.add("On Minimum Purchase Of $. 200")
        data.add("On Minimum Purchase Of $. 200")




        val adapter = VouchersAdapter(data)
        binding.vouchersRecyclerview.adapter = adapter
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.back -> {
                onBackPressed()
            }
           /* R.id.btnSave -> {
                val intent = Intent(this, MyOrdersActivity::class.java)
                startActivity(intent)
            }*/
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}