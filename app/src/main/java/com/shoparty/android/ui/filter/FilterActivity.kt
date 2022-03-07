package com.shoparty.android.ui.filter

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohammedalaa.seekbar.DoubleValueSeekBarView
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityFilterBinding
import com.shoparty.android.utils.SpacesItemDecoration


class FilterActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityFilterBinding

    private var recyvlerviewItemList=ArrayList<RecyclerView>()
    private var filterIconItem=ArrayList<TextView>()
    private lateinit var rvAdapter: FilterAdapter
    var color=false
    var size=false
    var age=false
    var gender=false
    var price=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding= DataBindingUtil.setContentView(this, R.layout.activity_filter)
        initialise()
    }

    private fun initialise() {

        binding.tvColor.setOnClickListener(this)
        binding.tvSize.setOnClickListener(this)
        binding.btnApplay.setOnClickListener(this)
        binding.infoTool.tvClearall.visibility=View.VISIBLE
        binding.infoTool.tvTitle.setText("Filter")
        binding.tvAge.setOnClickListener(this)
        binding.tvGender.setOnClickListener(this)
        binding.tvPrice.setOnClickListener(this)
        binding.clPrice.setOnClickListener(this)
        binding.doubleRangeSeekbar.currentMinValue=50
        binding.doubleRangeSeekbar.currentMaxValue=150
        recyvlerviewItemList.add(binding.rvColorRecyclarview)
        recyvlerviewItemList.add(binding.rvSizeRecyclarview)
        recyvlerviewItemList.add(binding.rvAgeRecyclarview)
        recyvlerviewItemList.add(binding.rvGenderRecyclarview)

        filterIconItem.add(binding.tvColor)
        filterIconItem.add(binding.tvSize)
        filterIconItem.add(binding.tvAge)
        filterIconItem.add(binding.tvGender)





        binding.doubleRangeSeekbar.setOnRangeSeekBarViewChangeListener(object :OnDoubleValueSeekBarChangeListener{
            override fun onStartTrackingTouch(
                seekBar: DoubleValueSeekBarView?,
                min: Int,
                max: Int
            ) {
                Toast.makeText(this@FilterActivity,min.toString()+max.toString(),Toast.LENGTH_LONG).show()
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

        size()
        age()
        gender()
        filtercolor()

        binding.infoTool.ivDrawerBack.setOnClickListener(this)

    }

    private fun filtercolor() {
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


        val gridLayoutManager = GridLayoutManager(this, 9)
        binding.rvColorRecyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterColorAdapter(data)
        }

    }

    private fun gender() {

        val data=ArrayList<String>()
        data.add("Babys")
        data.add("Girl")
        data.add("Unisex")
        data.add("Women")


        val gridLayoutManager = GridLayoutManager(this, 3)
        binding.rvGenderRecyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterGenderAdapter(data,context)
        }
    }

    private fun age() {
        val data=ArrayList<String>()
        data.add("Baby 0-2 Years")
        data.add("Toddler 2-4 Years")
        data.add("Adventures 5-7 Years")
        data.add("Pioneers 8+")
        val spanCount = 2 // 2 columns
        val spacing = 10 // 30px
        val includeEdge = false

        binding.rvAgeRecyclarview.addItemDecoration(
            SpacesItemDecoration(
                spanCount,
                spacing,
                includeEdge
            )
        )

        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.rvAgeRecyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterAgeAdapter(data,context)
        }
    }

    private fun size(){
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
        binding.rvSizeRecyclarview.addItemDecoration(
            SpacesItemDecoration(
                spanCount,
                spacing,
                includeEdge
            )
        )


        val gridLayoutManager = GridLayoutManager(this, 5)
       binding.rvSizeRecyclarview.apply {
           layoutManager = gridLayoutManager
           setHasFixedSize(true)
           isFocusable = false
           adapter = FilterSizeAdapter(data,context)
       }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
             R.id.tv_color -> {
                 goneHide(binding.rvColorRecyclarview)
                 iconGoneHide(binding.tvColor)
                 color=!color;
             }
            R.id.tv_size -> {
                goneHide(binding.rvSizeRecyclarview)
                iconGoneHide(binding.tvSize)
                size=!size
            }
            R.id.tv_age -> {
                goneHide(binding.rvAgeRecyclarview)
                iconGoneHide(binding.tvAge)
                age=!age;
            }
            R.id.tv_gender -> {
                goneHide(binding.rvGenderRecyclarview)
                iconGoneHide(binding.tvGender)
                gender=!gender
            }
            R.id.tv_price -> {
                binding.clPrice.visibility=View.VISIBLE
                binding.tvPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_spinner_down_aero, 0);
                binding.tvColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_back_icon, 0);
                binding.tvSize.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_back_icon, 0);
                binding.tvGender.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_back_icon, 0);
                binding.tvAge.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_back_icon, 0);


                binding.rvGenderRecyclarview.visibility=View.GONE
                binding.rvSizeRecyclarview.visibility=View.GONE
                binding.rvAgeRecyclarview.visibility=View.GONE
                binding.rvColorRecyclarview.visibility=View.GONE

            }
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
            R.id.btn_Applay -> {
             finish()
            }
        }
    }

    private fun iconGoneHide(clickFilterItem: TextView) {
        binding.tvPrice.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_back_icon,
            0
        );
        for (textview in filterIconItem){

            if (textview == clickFilterItem){
                textview.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_spinner_down_aero,
                    0
                );
            }else{
                textview.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_back_icon,
                    0
                );
            }
        }


    }

    //  private fun goneHide(clickRecyclerview: RecyclerView,doubleClickEvent:Boolean,defaboolean: Boolean = false) {
        private fun goneHide(clickRecyclerview: RecyclerView) {
              binding.clPrice.visibility=View.GONE
            for (recyclerview in recyvlerviewItemList) {

                if (recyclerview == clickRecyclerview) {
                recyclerview.visibility = View.VISIBLE
            } else {
                recyclerview.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}