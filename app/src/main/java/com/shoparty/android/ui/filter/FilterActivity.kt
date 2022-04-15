package com.shoparty.android.ui.filter

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.mohammedalaa.seekbar.DoubleValueSeekBarView
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityFilterBinding

import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.filter.age.AgeResponse
import com.shoparty.android.ui.filter.age.FilterAgeAdapter
import com.shoparty.android.ui.filter.color.FilterColorsAdapters
import com.shoparty.android.ui.filter.color.ColorsResponse
import com.shoparty.android.ui.filter.gender.FilterGenderAdapter
import com.shoparty.android.ui.filter.size.SizeAdapters

import com.shoparty.android.utils.SpacesItemDecoration
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

class FilterActivity : AppCompatActivity(),View.OnClickListener, RecyclerViewClickListener {
    private lateinit var binding: ActivityFilterBinding
    private lateinit var viewModel: FilterViewModel
    private var colorlist: ArrayList<ColorsResponse.Colors> = ArrayList()
    private var sizelist: ArrayList<String> = ArrayList()
    private var recyvlerviewItemList=ArrayList<RecyclerView>()
    private var filterIconItem=ArrayList<TextView>()
    var color=false
    var size=false
    var age=false
    var gender=false
    private var genderlist: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_filter)
        viewModel = ViewModelProvider(this, ViewModalFactory(application))[FilterViewModel::class.java]
        viewModel.colors()//color api call
        viewModel.sizes()//size api call
        viewModel.gender()//gender api call
        viewModel.age() //gender api call

        initialise()
        setObserver()
    }

    private fun initialise() {
        binding.tvColor.setOnClickListener(this)
        binding.tvSize.setOnClickListener(this)
        binding.btnApplay.setOnClickListener(this)
        binding.infoTool.tvClearall.visibility=View.VISIBLE
        binding.infoTool.tvTitle.text =getString(R.string.filter)
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
                max: Int)
            {
               // Toast.makeText(this@FilterActivity,min.toString()+max.toString(),Toast.LENGTH_LONG).show()
            }
            override fun onStopTrackingTouch(seekBar: DoubleValueSeekBarView?, min: Int, max: Int)
            {
                binding.tvUsdMin.text=getString(R.string.dollor)+min.toString()
                binding.tvUsdMax.text=getString(R.string.dollor)+max.toString()
            }
            override fun onValueChanged(seekBar: DoubleValueSeekBarView?, min: Int, max: Int, fromUser: Boolean)
            {
             //   Toast.makeText(this@FilterActivity,min.toString()+max.toString(),Toast.LENGTH_LONG).show()
            }
        })
        binding.infoTool.ivDrawerBack.setOnClickListener(this)

    }


    private fun setObserver() {
        viewModel.mColor.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()

                    if (response.data.isNullOrEmpty()) {
                        //no data
                    } else {
                        colorlist.clear()
                        colorlist = response.data as ArrayList<ColorsResponse.Colors>
                        setColorsListAdapter(colorlist)
                    }
                }
                is Resource.Loading -> {
                    com.shoparty.android.utils.ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.mSizes.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()

                    if (response.data.isNullOrEmpty()) {
                        //no data
                    } else {
                        sizelist.clear()
                        sizelist = response.data as ArrayList<String>
                        setSizeListAdapter(sizelist)
                    }
                }
                is Resource.Loading -> {
                    com.shoparty.android.utils.ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        viewModel.mGenders.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    if(response.data.isNullOrEmpty())
                    {
                        //no data
                    }
                    else
                    {
                        genderlist = response.data as ArrayList<String>
                        setGenderListAdapter(genderlist)
                    }
                }
                is Resource.Loading -> {
                    com.shoparty.android.utils.ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(applicationContext, response.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext, response.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        viewModel.mAges.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    setAgeData(response.data)
                }
                is Resource.Loading -> {
                    com.shoparty.android.utils.ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(applicationContext, response.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext, response.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun setColorsListAdapter(data: ArrayList<ColorsResponse.Colors>) {
        val gridLayoutManager = GridLayoutManager(this, 9)
        binding.rvColorRecyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterColorsAdapters(data,this@FilterActivity)
        }
    }

    private fun setSizeListAdapter(data: ArrayList<String>) {
        val gridLayoutManager = GridLayoutManager(this, 5)
        binding.rvSizeRecyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = SizeAdapters(this@FilterActivity,data,this@FilterActivity)
        }

    }

    private fun setGenderListAdapter(data: ArrayList<String>) {
        val gridLayoutManager = GridLayoutManager(this, 3)
        binding.rvGenderRecyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = FilterGenderAdapter(data,this@FilterActivity)
        } }


    private fun setAgeData(data: List<AgeResponse.Result>?) {
        val gridLayoutManager = GridLayoutManager(this, 3)
        binding.rvAgeRecyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(false)
            adapter = FilterAgeAdapter(data!!,context)
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
                binding.tvPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_aero_new_downs, 0);
                binding.tvColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_aerou_new_up, 0);
                binding.tvSize.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_aerou_new_up, 0);
                binding.tvGender.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_aerou_new_up, 0);
                binding.tvAge.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_aerou_new_up, 0);


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
            R.drawable.ic_aerou_new_up,
            0
        );
        for (textview in filterIconItem){

            if (textview == clickFilterItem){
                textview.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_aero_new_downs,
                    0
                );
            }else{
                textview.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_aerou_new_up,
                    0
                );
            }
        }


    }

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

    override fun click(pos: String) {
    Toast.makeText(this,pos,Toast.LENGTH_LONG).show()
    }
}