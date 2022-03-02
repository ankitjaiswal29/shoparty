package com.shoparty.android.ui.productdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityCancelConfirmBinding
import com.shoparty.android.databinding.ActivityProductDetailsBinding
import com.shoparty.android.ui.customize.CustomizeActivity
import com.shoparty.android.ui.filter.FilterColorAdapter
import com.shoparty.android.ui.main.deals.TopSellingHomeModel
import com.shoparty.android.ui.main.home.MySliderImageAdapter
import com.shoparty.android.ui.main.home.TopSellingHomeAdapter
import com.shoparty.android.ui.shoppingbag.ShopingBagActivity
import com.smarteist.autoimageslider.SliderView

class ProductDetailsActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_product_details)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.ivBagBtn.visibility=View.VISIBLE
        binding.btnCostomizeit.setOnClickListener(this)
        binding.tvAddtobag.setOnClickListener(this)
        binding.infoTool.ivBagBtn.setOnClickListener(this)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.infoTool.tvTitle.setText("Tesla Toys")

        val imageList: ArrayList<String> = ArrayList()
        imageList.add("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg")
        imageList.add("https://images.ctfassets.net/hrltx12pl8hq/4plHDVeTkWuFMihxQnzBSb/aea2f06d675c3d710d095306e377382f/shutterstock_554314555_copy.jpg")
        imageList.add("https://media.istockphoto.com/photos/child-hands-formig-heart-shape-picture-id951945718?k=6&m=951945718&s=612x612&w=0&h=ih-N7RytxrTfhDyvyTQCA5q5xKoJToKSYgdsJ_mHrv0=")
        setImageInSlider(imageList, binding.imageSliderr)

       choesColorRecyclaritem()
        recyclar1()
        recyclar2()


    }

    private fun choesColorRecyclaritem() {
        val data=ArrayList<String>()
        data.add("#FFBB86FC")
        data.add("#606060")
        data.add("#FFBB86FC")
        data.add("#FFBB86FC")
        data.add("#E30986")


        val gridLayoutManager = GridLayoutManager(this, 9)
        binding.rvColorrecyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterColorAdapter(data)
        }

    }

    private fun recyclar1() {
        val topSellingItemList = listOf<TopSellingHomeModel>(
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
        )
        binding.rvProductdetailsRecyclarview.adapter = ProductdetailsAdapter(topSellingItemList)
    }

    private fun recyclar2() {
        val topSellingItemList = listOf<TopSellingHomeModel>(
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
        )
        binding.rvProductdetailsRecyclarview2.adapter = ProductdetailsAdapter(topSellingItemList)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_drawer_back->{
                onBackPressed()
            }
            R.id.btn_costomizeit->{
                val intent = Intent (this, CustomizeActivity::class.java)
                startActivity(intent)
                binding.tvAddtobag.setOnClickListener(this)
                binding.infoTool.ivBagBtn.setOnClickListener(this)
                binding.infoTool.ivDrawerBack.setOnClickListener(this)
            }
            R.id.tv_addtobag->{
                val intent = Intent(this, ShopingBagActivity::class.java)
                startActivity(intent)
            }
            R.id.ivBagBtn->{
                val intent = Intent(this, ShopingBagActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_drawer_back->{
              onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = MySliderImageAdapter()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
    }
}