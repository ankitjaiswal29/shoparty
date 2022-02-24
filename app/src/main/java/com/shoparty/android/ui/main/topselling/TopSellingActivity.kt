package com.shoparty.android.ui.main.topselling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityTopSellingBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.main.deals.TopSellingHomeModel
import com.shoparty.android.ui.myaccount.MyAccountModel
import kotlinx.android.synthetic.main.fragment_deals.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*

class TopSellingActivity : AppCompatActivity(), View.OnClickListener,RecyclerViewClickListener {
    private lateinit var binding: ActivityTopSellingBinding
    lateinit var dialog:BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_top_selling)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_top_selling)
        initialise()

    }
    private fun initialise() {
        binding.infoTool.ivBagBtn.visibility=View.VISIBLE
        binding.infoTool.ivBtnsearch.visibility=View.VISIBLE
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.tvSort.setOnClickListener(this)
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
            R.id.tv_sort -> {
                showBottomsheetDialog()
            }
        }
    }

    private fun showBottomsheetDialog() {

        dialog = BottomSheetDialog(this)

        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.top_selling_bottomsheet_layout, null)

        // on below line we are creating a variable for our button
        // which we are using to dismiss our dialog.
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_top_selling_bottomsheetrecyclar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<String>()
        data.add("Newest To Oldest")
        data.add("Oldest To Newest")
        data.add("Price - Low To High")
        data.add("Price - High To Low")
        val adapter=TopSellingBottomSheetAdapter(data,this)
        recyclerView.adapter=adapter

        /* btnClose.setOnClickListener {
            // on below line we are calling a dismiss
            // method to close our dialog.
            dialog.dismiss()
        }*/
        // below line is use to set cancelable to avoid
        // closing of dialog box when clicking on the screen.
        dialog.setCancelable(true)

        // on below line we are setting
        // content view to our view.
        dialog.setContentView(view)

        // on below line we are calling
        // a show method to display a dialog.
        dialog.show()


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

    override fun click(pos: String) {
        Toast.makeText(this,pos,Toast.LENGTH_LONG).show()
        dialog.dismiss();
    }

}