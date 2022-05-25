package com.shoparty.android.ui.filter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohammedalaa.seekbar.DoubleValueSeekBarView
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityFilterBinding

import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.filter.age.AgeRequest
import com.shoparty.android.ui.filter.age.AgeResponse
import com.shoparty.android.ui.filter.age.FilterAgeAdapter
import com.shoparty.android.ui.filter.color.ColorsResponse
import com.shoparty.android.ui.filter.color.FilterColorsAdapters
import com.shoparty.android.ui.filter.gender.FilterGenderAdapter
import com.shoparty.android.ui.filter.size.SizeAdapters
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

class FilterActivity : AppCompatActivity(), View.OnClickListener, QuantityListner,
    RecyclerViewClickListener {
    private var selectedAgeList: ArrayList<AgeRequest>? = ArrayList()
    private lateinit var binding: ActivityFilterBinding
    private lateinit var viewModel: FilterViewModel
    private var colorlist: ArrayList<ColorsResponse.Colors> = ArrayList()
    private var sizelist: ArrayList<String> = ArrayList()
    private var recyvlerviewItemList = ArrayList<RecyclerView>()
    private var filterIconItem = ArrayList<TextView>()
    private var selectedColorList: ArrayList<String> = ArrayList()
     private var selectedSizeList: ArrayList<String> = ArrayList()
     private var selectedGenderList: ArrayList<String> = ArrayList()
    private var genderlist: ArrayList<String> = ArrayList()
    var selectedminprice =0
    var selectedmaxprice = 0
    private lateinit var adapterColor: FilterColorsAdapters
    var color = false
    var size = false
    var age = false
    var gender = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            binding.clfilterRootlayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.infoTool.ivDrawerBack.rotation = 0F
        }else {
            binding.clfilterRootlayout.layoutDirection = View.LAYOUT_DIRECTION_LTR
            binding.infoTool.ivDrawerBack.rotation = 180F
        }
        viewModel = ViewModelProvider(this, ViewModalFactory(application))[FilterViewModel::class.java]
        viewModel.colors()//color api call
        viewModel.sizes()//size api call
        viewModel.gender()//gender api call
        viewModel.age() //gender api call

        initialise()
        setObserver()
    }

    private fun initialise() {

        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            binding.clfilterRootlayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.clfilterRootlayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.clfilterRootlayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }else {
            binding.clfilterRootlayout.layoutDirection = View.LAYOUT_DIRECTION_LTR
            binding.clfilterRootlayout.layoutDirection = View.LAYOUT_DIRECTION_LTR
            binding.clfilterRootlayout.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }

        if (intent.extras !=null)
        {
            selectedColorList = intent.getStringArrayListExtra("colorList") as ArrayList<String>
        }
        binding.tvColor.setOnClickListener(this)
        binding.tvSize.setOnClickListener(this)
        binding.btnApply.setOnClickListener(this)
        binding.infoTool.tvClearall.visibility = View.VISIBLE
        binding.infoTool.tvTitle.text = getString(R.string.filter)
        binding.tvAge.setOnClickListener(this)
        binding.tvGender.setOnClickListener(this)
        binding.tvPrice.setOnClickListener(this)
        binding.clPrice.setOnClickListener(this)
        binding.infoTool.tvClearall.setOnClickListener(this)
        binding.doubleRangeSeekbar.currentMinValue = getString(R.string.minvalue).toInt()
        binding.doubleRangeSeekbar.currentMaxValue = getString(R.string.maxvalue).toInt()

        recyvlerviewItemList.add(binding.rvColorRecyclarview)
        recyvlerviewItemList.add(binding.rvSizeRecyclarview)
        recyvlerviewItemList.add(binding.rvAgeRecyclarview)
        recyvlerviewItemList.add(binding.rvGenderRecyclarview)
        filterIconItem.add(binding.tvColor)
        filterIconItem.add(binding.tvSize)
        filterIconItem.add(binding.tvAge)
        filterIconItem.add(binding.tvGender)

        binding.doubleRangeSeekbar.setOnRangeSeekBarViewChangeListener(object :
            OnDoubleValueSeekBarChangeListener {
            override fun onStartTrackingTouch(
                seekBar: DoubleValueSeekBarView?,
                min: Int,
                max: Int
            ) {
                // Toast.makeText(this@FilterActivity,min.toString()+max.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onStopTrackingTouch(seekBar: DoubleValueSeekBarView?, min: Int, max: Int) {
                binding.tvUsdMin.text = getString(R.string.dollor) + min.toString()
                binding.tvUsdMax.text = getString(R.string.dollor) + max.toString()
                selectedminprice = min
                selectedmaxprice = max
            }

            override fun onValueChanged(
                seekBar: DoubleValueSeekBarView?,
                min: Int,
                max: Int,
                fromUser: Boolean
            ) {
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
                    if (response.data.isNullOrEmpty()) {
                        //no data
                    } else {
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
        binding.rvColorRecyclarview.setHasFixedSize(true)
        binding.rvColorRecyclarview.layoutManager = GridLayoutManager(
            this,
            9,
            RecyclerView.VERTICAL,
            false
        )
        adapterColor = FilterColorsAdapters(data, quantityListner = this,this)
        binding.rvColorRecyclarview.adapter = adapterColor
    }

    private fun setSizeListAdapter(data: ArrayList<String>) {
        val gridLayoutManager = GridLayoutManager(this, 5)
        binding.rvSizeRecyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = SizeAdapters(this@FilterActivity, data, this@FilterActivity)
        }

    }

    private fun setGenderListAdapter(data: ArrayList<String>) {
        val gridLayoutManager = GridLayoutManager(this, 3)
        binding.rvGenderRecyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = FilterGenderAdapter(data, this@FilterActivity, this@FilterActivity)
        }
    }


    private fun setAgeData(data: List<AgeResponse.Result>?) {
        val gridLayoutManager = GridLayoutManager(this, 3)
        binding.rvAgeRecyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(false)
            adapter = FilterAgeAdapter(data!!, context, this@FilterActivity)
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_color -> {
                goneHide(binding.rvColorRecyclarview)
                iconGoneHide(binding.tvColor)
                color = !color;
            }
            R.id.tv_size -> {
                goneHide(binding.rvSizeRecyclarview)
                iconGoneHide(binding.tvSize)
                size = !size
            }
            R.id.tv_age -> {
                goneHide(binding.rvAgeRecyclarview)
                iconGoneHide(binding.tvAge)
                age = !age;
            }
            R.id.tv_gender -> {
                goneHide(binding.rvGenderRecyclarview)
                iconGoneHide(binding.tvGender)
                gender = !gender
            }
            R.id.tv_price -> {
                binding.clPrice.visibility = View.VISIBLE
                binding.tvPrice.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_aero_new_downs,
                    0
                );
                binding.tvColor.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_aerou_new_up,
                    0
                );
                binding.tvSize.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_aerou_new_up,
                    0
                );
                binding.tvGender.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_aerou_new_up,
                    0
                );
                binding.tvAge.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_aerou_new_up,
                    0
                );


                binding.rvGenderRecyclarview.visibility = View.GONE
                binding.rvSizeRecyclarview.visibility = View.GONE
                binding.rvAgeRecyclarview.visibility = View.GONE
                binding.rvColorRecyclarview.visibility = View.GONE

            }
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
            R.id.btnApply -> {
                val intent = Intent()
                intent.putStringArrayListExtra("colorList", selectedColorList)
                intent.putParcelableArrayListExtra("ageList",selectedAgeList)
                intent.putStringArrayListExtra("sizeList", selectedSizeList)
                intent.putStringArrayListExtra("genderList", selectedGenderList)
                intent.putExtra("selectedminprice", selectedminprice)
                intent.putExtra("selectedmaxprice", selectedmaxprice)
                setResult(Activity.RESULT_OK, intent)
                finish()

            }

            R.id.tv_clearall -> {
                clearAllFilter()
            }
        }
    }

    private fun clearAllFilter() {
        selectedColorList.clear()
        selectedSizeList.clear()
        genderlist.clear()
        selectedAgeList == null
        selectedminprice = 0
        selectedmaxprice = 0
        Utils.showLongToast(this, getString(R.string.clearsuccess))
        finish()
    }

    private fun iconGoneHide(clickFilterItem: TextView) {
        binding.tvPrice.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_aerou_new_up,
            0
        );
        for (textview in filterIconItem) {

            if (textview == clickFilterItem) {
                textview.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_aero_new_downs,
                    0
                );
            } else {
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
        binding.clPrice.visibility = View.GONE
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
        Toast.makeText(this, pos, Toast.LENGTH_LONG).show()
    }

    override fun onColorQuantitychanged(userlist: ArrayList<String>) {
        selectedColorList.clear()
        selectedColorList.addAll(userlist.toList())
      //  Toast.makeText(this, "" + selectedColorList, Toast.LENGTH_SHORT).show()
    }

    override fun onSizeQuantitychanged(userlist: ArrayList<String>) {
        selectedSizeList.clear()
        selectedSizeList.addAll(userlist.toList())
     //   Toast.makeText(this, "" + selectedSizeList, Toast.LENGTH_SHORT).show()
    }

    override fun onAgeQuantitychanged(userlist: ArrayList<AgeRequest>) {
        selectedAgeList = userlist
    }

    override fun onGenderQuantitychanged(userlist: ArrayList<String>) {
        selectedGenderList.clear()
        selectedGenderList.addAll(userlist.toList())
    }


}