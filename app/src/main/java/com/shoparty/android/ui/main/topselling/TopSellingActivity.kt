package com.shoparty.android.ui.main.topselling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityTopSellingBinding
import com.shoparty.android.ui.main.deals.TopSellingHomeModel
import kotlinx.android.synthetic.main.fragment_deals.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*

class TopSellingActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTopSellingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_top_selling)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_top_selling)
        initialise()

    }
    private fun initialise() {
        binding.infoTool.iv_bag_btn.visibility=View.VISIBLE
        binding.infoTool.iv_btnsearch.visibility=View.VISIBLE
        binding.infoTool.iv_drawer_back.setOnClickListener(this)
        Topsellingitem()


    }
    override fun onClick(v: View?) {
        when(v?.id){
            /* R.id.btnCancel -> {
                 val intent = Intent(this, CancelOrderActivity::class.java)
                 intent.putExtra("key","Ongoeing")
                 startActivity(intent)
             }*/
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
        }
    }
    private fun Topsellingitem() {
        val naItemList = listOf<TopSellingHomeModel>(
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),

            )

        val gridLayoutManager = GridLayoutManager(this, 2)
        deals_item_recycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = TopSellingAdapter(naItemList)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}