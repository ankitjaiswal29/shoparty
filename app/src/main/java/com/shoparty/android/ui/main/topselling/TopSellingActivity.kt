package com.shoparty.android.ui.main.topselling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mohammedalaa.seekbar.DoubleValueSeekBarView
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityTopSellingBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.interfaces.RecyclerViewTopSellingClickListener
import com.shoparty.android.ui.filter.*
import com.shoparty.android.ui.main.deals.TopSellingHomeModel

import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.ui.search.SearchActivity
import com.shoparty.android.ui.shoppingbag.ShopingBagActivity
import com.shoparty.android.utils.SpacesItemDecoration
import kotlinx.android.synthetic.main.bottomsheet_filter_layout.view.*
import kotlinx.android.synthetic.main.fragment_deals.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*

class TopSellingActivity : AppCompatActivity(), View.OnClickListener,RecyclerViewClickListener,RecyclerViewTopSellingClickListener {
    private lateinit var binding: ActivityTopSellingBinding
    lateinit var dialog:BottomSheetDialog
    var color=false
    var size=false
    var age=false
    var gender=false
    var price=false
    private var recyvlerviewItemList=ArrayList<RecyclerView>()
    private var filterIconItem=ArrayList<TextView>()
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
        binding.infoTool.ivBagBtn.setOnClickListener(this)
        binding.infoTool.ivBtnsearch.setOnClickListener(this)
        binding.tvFilter.setOnClickListener(this)
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
            R.id.tv_filter -> {

               // showBottomsheetFilter()
                val intent = Intent(this, FilterActivity::class.java)
                startActivity(intent)
            }
            R.id.ivBagBtn -> {
                val intent = Intent(this, ShopingBagActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_btnsearch -> {
                val intent = Intent (this, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun showBottomsheetFilter() {

        val view = layoutInflater.inflate(R.layout.bottomsheet_filter_layout, null)
        dialog = BottomSheetDialog(this,R.style.BottomSheetDialog)
        val tv_color = view.findViewById<TextView>(R.id.tv_color)
        val tvsize = view.findViewById<TextView>(R.id.tv_size)
        val tvage = view.findViewById<TextView>(R.id.tv_age)
        val tvgender = view.findViewById<TextView>(R.id.tv_gender)
        val tvprice = view.findViewById<TextView>(R.id.tv_price)
        val double_range_seekbar = view.findViewById<DoubleValueSeekBarView>(R.id.double_range_seekbar)
        tv_color.setOnClickListener(this)
        tvsize.setOnClickListener(this)
        tvage.setOnClickListener(this)
        tvgender.setOnClickListener(this)
        tvprice.setOnClickListener(this)
        view.tv_color.setOnClickListener {
            goneHide(view.rv_color_recyclarview,view)
            iconGoneHide(view.tv_color,view)
            color=!color;
        }
        view.tv_size.setOnClickListener {
            goneHide(view.rv_size_recyclarview,view)
            iconGoneHide(view.tv_size,view)
            size=!size

        }
        view.tv_age.setOnClickListener {
            goneHide(view.rv_age_recyclarview,view)
            iconGoneHide(view.tv_age,view)
            age=!age;

        }
        view.tv_gender.setOnClickListener {
            goneHide(view.rv_gender_recyclarview,view)
            iconGoneHide(view.tv_gender,view)
            gender=!gender

        }
        view.tv_price.setOnClickListener {
            view.cl_price.visibility=View.VISIBLE
            view. tv_price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_aeroup, 0);
            view.tv_color.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_aeroup, 0);
            view.tv_size.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_aeroup, 0);
            view.tv_gender.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_aeroup, 0);
            view.tv_age.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_aeroup, 0);


            view.rv_gender_recyclarview.visibility=View.VISIBLE
            view.rv_size_recyclarview.visibility=View.VISIBLE
            view.rv_age_recyclarview.visibility=View.VISIBLE
            view.rv_color_recyclarview.visibility=View.VISIBLE

        }

        double_range_seekbar.currentMinValue=50
        double_range_seekbar.currentMaxValue=150
        recyvlerviewItemList.add(view.rv_color_recyclarview)
        recyvlerviewItemList.add(view.rv_size_recyclarview)
        recyvlerviewItemList.add(view.rv_age_recyclarview)
        recyvlerviewItemList.add(view.rv_gender_recyclarview)


        filterIconItem.add(view.tv_color)
        filterIconItem.add(view.tv_size)
        filterIconItem.add(view.tv_age)
        filterIconItem.add(view.tv_gender)


        double_range_seekbar.setOnRangeSeekBarViewChangeListener(object :
            OnDoubleValueSeekBarChangeListener {
            override fun onStartTrackingTouch(
                seekBar: DoubleValueSeekBarView?,
                min: Int,
                max: Int
            ) {
                Toast.makeText(this@TopSellingActivity,min.toString()+max.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onStopTrackingTouch(seekBar: DoubleValueSeekBarView?, min: Int, max: Int) {

            }

            override fun onValueChanged(
                seekBar: DoubleValueSeekBarView?,
                min: Int,
                max: Int,
                fromUser: Boolean
            ) {

            }

        })

        size(view)
        age(view)
        gender(view)
        filtercolor(view)

        dialog.setCancelable(true)

        // on below line we are setting
        // content view to our view.
        dialog.setContentView(view)

        // on below line we are calling
        // a show method to display a dialog.
        dialog.show()

    }
    private fun filtercolor(view: View) {
        val data=ArrayList<String>()
        data.add("#FFBB86FC")
        data.add("#606060")
        data.add("#FFBB86FC")
        data.add("#FFBB86FC")
        data.add("#E30986")
        data.add("#FFBB86FC")
        data.add("#606060")
        data.add("#E30986")
        data.add("#FFBB86FC")
        data.add("#606060")





        // val rv_color_recyclarview = view?.findViewById<RecyclerView>(R.id.rv_color_recyclarview)

        val gridLayoutManager = GridLayoutManager(this, 9)
        view.rv_color_recyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterColorAdapter(data)
        }

    }
    private fun gender(view: View) {

        val data=ArrayList<String>()
        data.add("Babys")
        data.add("Girl")
        data.add("Unisex")
        data.add("Women")


        val gridLayoutManager = GridLayoutManager(this, 4)
        view. rv_gender_recyclarview?.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterGenderAdapter(data,context)
        }
    }
    private fun age(view: View) {
        val data=ArrayList<String>()
        data.add("Baby 0-2 Years")
        data.add("Toddler 2-4 Years")
        data.add("Adventures 5-7 Years")
        data.add("Pioneers 8+")
        val spanCount = 2 // 2 columns
        val spacing = 10 // 30px
        val includeEdge = false

        view. rv_age_recyclarview?.addItemDecoration(
            SpacesItemDecoration(
                spanCount,
                spacing,
                includeEdge
            )
        )

        val gridLayoutManager = GridLayoutManager(this, 2)
        view.rv_age_recyclarview?.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterAgeAdapter(data,context)
        }
    }

    private fun size(view: View) {
        val data=ArrayList<String>()
        data.add("S")
        data.add("M")
        data.add("XL")
        data.add("XXL")
        data.add("UK6")
        data.add("UK7")
        data.add("UK8")
        data.add("UK9")
        data.add("UK10")

        val spanCount = 5 // 2 columns
        val spacing = 10 // 30px
        val includeEdge = false

        view.rv_size_recyclarview?.addItemDecoration(
            SpacesItemDecoration(
                spanCount,
                spacing,
                includeEdge
            )
        )


        val gridLayoutManager = GridLayoutManager(this, 5)
        view. rv_size_recyclarview?.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterSizeAdapter(data,context)
        }
    }
    private fun goneHide(clickRecyclerview: RecyclerView, view: View) {
        view.cl_price.visibility=View.VISIBLE
        for (recyclerview in recyvlerviewItemList)
        {

            if (recyclerview == clickRecyclerview) {
                recyclerview.visibility=View.VISIBLE
            } else {
                recyclerview.visibility= View.VISIBLE
            }
        }
    }

    private fun iconGoneHide(clickFilterItem: TextView, view: View) {
        view.tv_price.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_icon_aeroup,
            0
        );
        for (textview in filterIconItem){

            if (textview == clickFilterItem){
                textview.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_icon_aeroup,
                    0
                );
            }else{
                textview.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_icon_aeroup,
                    0
                );
            }
        }


    }

    private fun showBottomsheetDialog() {



        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.top_selling_bottomsheet_layout, null)
        dialog = BottomSheetDialog(this,R.style.BottomSheetDialog)
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
            adapter = TopSellingAdapter(naItemList,this@TopSellingActivity)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun click(pos: String) {
        Toast.makeText(this,pos,Toast.LENGTH_LONG).show()
        dialog.dismiss();
    }

    override fun itemclick(pos: String) {
        val intent = Intent (this, ProductDetailsActivity::class.java)
        startActivity(intent)
    }

}